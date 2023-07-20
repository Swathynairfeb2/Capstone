package com.example.planahead_capstone;

import static com.example.planahead_capstone.R.id.titleTextView;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.planahead_capstone.DatabaseHelper;
import com.example.planahead_capstone.R;
import com.example.planahead_capstone.TodoTask;

import java.util.List;

public class TodoTaskAdapter extends ArrayAdapter<TodoTask> {

    private Context context;
    private List<TodoTask> tasks;
    private DatabaseHelper databaseHelper;
    private String eventid;

    public TodoTaskAdapter(Context context, List<TodoTask> tasks, String eventId) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
        this.databaseHelper = new DatabaseHelper(context);
        this.eventid = eventId;
        loadTasksFromDatabase();
    }

    public void addTask(TodoTask task) {
        tasks.add(task);
        notifyDataSetChanged();
    }

    private void loadTasksFromDatabase() {
        tasks.clear(); // Clear the existing tasks list

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_TODO_ID, DatabaseHelper.COLUMN_TODO_NAME, DatabaseHelper.COLUMN_TODO_COMPLETED};
        String selection = DatabaseHelper.COLUMN_EVENTID + " = ?";
        String[] selectionArgs = {String.valueOf(eventid)};
        Cursor cursor = db.query(DatabaseHelper.TABLE_TODO, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int taskIdColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TODO_ID);
            int taskNameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TODO_NAME);
            int taskCompletedColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TODO_COMPLETED);

            int taskId = cursor.getInt(taskIdColumnIndex);
            String taskName = cursor.getString(taskNameColumnIndex);
            boolean taskCompleted = cursor.getInt(taskCompletedColumnIndex) == 1;

            TodoTask task = new TodoTask(taskId, taskName, taskCompleted, eventid);
            tasks.add(task);
        }

        cursor.close();
        db.close();

        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_todo, parent, false);
        }

        TodoTask task = tasks.get(position);

        CheckBox checkBox = view.findViewById(R.id.taskCheckbox);
        checkBox.setChecked(task.isCompleted());
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            updateTaskCompletion((int) task.getId(), isChecked);
        });

        ImageView deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> deleteTask((int) task.getId()));

        TextView taskNameTextView = view.findViewById(R.id.taskNameTextView);
        taskNameTextView.setText(task.getName());

        // Edit button
        ImageView editButton = view.findViewById(R.id.editButton);
        editButton.setOnClickListener(v -> showEditDialog(task));

        return view;
    }
    private void showEditDialog(TodoTask task) {
        // Create a custom dialog to edit the task
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_task, null);
        builder.setView(dialogView);

        // Find the EditText view by its ID
        EditText taskEditText = dialogView.findViewById(R.id.taskEditText);

        // Set the initial task name in the EditText
        taskEditText.setText(task.getName());

        // Set up the buttons for saving or canceling the edit
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newTaskName = taskEditText.getText().toString().trim();
            if (!newTaskName.isEmpty()) {
                task.setName(newTaskName);
                updateTaskName((int) task.getId(), newTaskName);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Task name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);

        // Set the dialog background color
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.purpleb);

        // Customize the button colors
        dialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            positiveButton.setTextColor(ContextCompat.getColor(context, R.color.peachbutton));
            negativeButton.setTextColor(ContextCompat.getColor(context, R.color.peachbutton));
        });

        // Show the dialog
        dialog.show();
    }




    private void updateTaskName(int taskId, String taskName) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TODO_NAME, taskName);

        String whereClause = DatabaseHelper.COLUMN_TODO_ID + " = ?";
        String[] whereArgs = {String.valueOf(taskId)};

        db.update(DatabaseHelper.TABLE_TODO, values, whereClause, whereArgs);

        db.close();
    }
    private void updateTaskCompletion(int taskId, boolean isCompleted) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TODO_COMPLETED, isCompleted ? 1 : 0);

        String whereClause = DatabaseHelper.COLUMN_TODO_ID + " = ?";
        String[] whereArgs = {String.valueOf(taskId)};

        db.update(DatabaseHelper.TABLE_TODO, values, whereClause, whereArgs);

        db.close();

        // Refresh the task list
        loadTasksFromDatabase();
    }

    private void deleteTask(int taskId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String whereClause = DatabaseHelper.COLUMN_TODO_ID + " = ?";
        String[] whereArgs = {String.valueOf(taskId)};

        db.delete(DatabaseHelper.TABLE_TODO, whereClause, whereArgs);

        db.close();

        loadTasksFromDatabase(); // Reload tasks from the database
    }
}
