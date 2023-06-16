//package com.example.planahead_capstone;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.view.View;
////
////import android.widget.ImageView;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////public class EventDetailPage extends AppCompatActivity {
////    private ImageView addTaskImage;
////
////        @Override
////        protected void onCreate(Bundle savedInstanceState) {
////
////            super.onCreate(savedInstanceState);
////            setContentView(R.layout.activity_eventdetailpage);
////            addTaskImage=findViewById(R.id.toDoImageView);
////
////            addTaskImage.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    openTodoListPage();
////                }
////            });
////        }
////
////        private void openTodoListPage() {
////            Intent intent = new Intent(this, TodoListActivity.class);
////            startActivity(intent);
////        }
////    }
////
////
//package com.example.planahead_capstone;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class EventDetailPage extends AppCompatActivity {
//    private ImageView addTaskImage;
//    private TextView invitationTextView;
//    private DatabaseHelper databaseHelper;
//
//    private int eventId; // Add a field to store the event ID
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_eventdetailpage);
//
//        addTaskImage = findViewById(R.id.toDoImageView);
//        invitationTextView = findViewById(R.id.invitationTextView);
//        databaseHelper = new DatabaseHelper(this);
//
//        addTaskImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openTodoListPage();
//            }
//        });
//
//        // Retrieve the event ID from the intent
//        Intent intent = getIntent();
//        if (intent != null) {
//            eventId = intent.getIntExtra("event_id", -1);
//        }
//
//        // Retrieve the event details from the database
//        Event event = databaseHelper.getEventById(eventId);
//        if (event != null) {
//            String invitationText = generateInvitationText(event);
//            invitationTextView.setText(invitationText);
//        }
//    }
//
//    private void openTodoListPage() {
//        Intent intent = new Intent(this, TodoListActivity.class);
//        startActivity(intent);
//    }
//
//    private String generateInvitationText(Event event) {
//        // Generate the invitation text based on the event details
//        // You can customize this method according to your requirements
//        StringBuilder invitationText = new StringBuilder();
//        invitationText.append("Event Name: ").append(event.getName()).append("\n");
//        invitationText.append("Location: ").append(event.getLocation()).append("\n");
//        invitationText.append("Date: ").append(event.getDate()).append("\n");
//        invitationText.append("Time: ").append(event.getTime()).append("\n");
//        invitationText.append("Budget: ").append(event.getBudget()).append("\n");
//
//        return invitationText.toString();
//    }
//}
package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class EventDetailPage extends AppCompatActivity {

    private ImageView addTaskImage;
    private TextView invitationTextView;
    private DatabaseHelper databaseHelper;

    private int eventId; // Add a field to store the event ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetailpage);

        addTaskImage = findViewById(R.id.toDoImageView);
     //   invitationTextView = findViewById(R.id.invitationTextView);
        databaseHelper = new DatabaseHelper(this);

        addTaskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodoListPage();
            }
        });


            }


            // Retrieve the event ID from the intent



    private void openTodoListPage() {
        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
    }

//    private String generateInvitationText(Event event) {
//        // Generate the invitation text based on the event details
//        // You can customize this method according to your requirements
//        StringBuilder invitationText = new StringBuilder();
//        invitationText.append("Event Name: ").append(event.getName()).append("\n");
//        invitationText.append("Location: ").append(event.getLocation()).append("\n");
//        invitationText.append("Date: ").append(event.getDate()).append("\n");
//        invitationText.append("Time: ").append(event.getTime()).append("\n");
//        invitationText.append("Budget: ").append(event.getBudget()).append("\n");
//
//        return invitationText.toString();
//    }
}







