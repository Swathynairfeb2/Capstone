package com.example.planahead_capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends Activity {

    private ListView todoListView;
    private Button addTaskButton;

    private List<TodoTask> tasks;
    private TodoTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_page);

        todoListView = findViewById(R.id.todoListView);
        addTaskButton = findViewById(R.id.addTaskButton);

        tasks = new ArrayList<>();

        adapter = new TodoTaskAdapter(this, tasks);
        todoListView.setAdapter(adapter);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AddTaskActivity to add a new task
                Intent intent = new Intent(TodoListActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoTask task = tasks.get(position);
                Toast.makeText(TodoListActivity.this, "Clicked: " + task.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String taskName = data.getStringExtra("taskName");

            if (taskName != null && !taskName.isEmpty()) {
                TodoTask todoTask = new TodoTask(taskName, false);
                tasks.add(todoTask);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
