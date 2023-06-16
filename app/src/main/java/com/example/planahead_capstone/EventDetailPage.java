package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EventDetailPage extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_eventdetailpage);

            Button todoButton = findViewById(R.id.todoButton);
            todoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTodoListPage();
                }
            });
        }

        private void openTodoListPage() {
            Intent intent = new Intent(this, TodoListActivity.class);
            startActivity(intent);
        }
    }


