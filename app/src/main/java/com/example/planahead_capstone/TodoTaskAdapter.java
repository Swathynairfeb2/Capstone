package com.example.planahead_capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TodoTaskAdapter extends ArrayAdapter<TodoTask> {

    private Context context;
    private List<TodoTask> tasks;
    private DatabaseHelper databaseHelper;

    public TodoTaskAdapter(Context context, List<TodoTask> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
        this.databaseHelper = new DatabaseHelper(context);
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
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setCompleted(checkBox.isChecked());
                databaseHelper.updateTask(task.getId(), task.isCompleted());
            }
        });

        TextView taskNameTextView = view.findViewById(R.id.taskNameTextView);
        taskNameTextView.setText(task.getName());

        return view;
    }
}
