package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EventDetailPage extends AppCompatActivity {
    private ImageView addTaskImage;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_eventdetailpage);
            addTaskImage=findViewById(R.id.toDoImageView);

            addTaskImage.setOnClickListener(new View.OnClickListener() {
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


