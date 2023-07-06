package com.example.planahead_capstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        Toast.makeText(context, eventid, Toast.LENGTH_SHORT).show();

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

        return view;
    }

    private void updateTaskCompletion(int taskId, boolean isCompleted) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TODO_COMPLETED, isCompleted ? 1 : 0);

        String whereClause = DatabaseHelper.COLUMN_TODO_ID + " = ?";
        String[] whereArgs = {String.valueOf(taskId)};

        db.update(DatabaseHelper.TABLE_TODO, values, whereClause, whereArgs);

        db.close();
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