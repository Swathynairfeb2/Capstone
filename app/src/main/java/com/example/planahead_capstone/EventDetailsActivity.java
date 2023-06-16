package com.example.planahead_capstone;
////
////import android.os.Bundle;
////import android.widget.TextView;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////public class EventDetailsActivity extends AppCompatActivity {
////
////    private TextView eventNameTextView;
////    private TextView eventLocationTextView;
////    private TextView eventDateTextView;
////    private TextView eventTimeTextView;
////    private TextView eventBudgetTextView;
////
////    private DatabaseHelper databaseHelper;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_eventdetailpage);
////
////        // Initialize views
////        eventNameTextView = findViewById(R.id.eventNameTextView);
////        eventLocationTextView = findViewById(R.id.eventLocationTextView);
////        eventDateTextView = findViewById(R.id.eventDateTextView);
////        eventTimeTextView = findViewById(R.id.eventTimeTextView);
////       // eventBudgetTextView = findViewById(R.id.editTextEventBudget);
////
////        // Initialize database helper
////        databaseHelper = new DatabaseHelper(this);
////
////        // Retrieve the event ID from the intent
////        long eventId = getIntent().getLongExtra("event_id", -1);
////
////        // Fetch the event details from the database
////        Event event = databaseHelper.getEventById(eventId);
////
////        if (event != null) {
////            // Update the UI with the event details
////            eventNameTextView.setText(event.getEventName());
////            eventLocationTextView.setText(event.getEventLocation());
////            eventDateTextView.setText(event.getEventDate());
////            eventTimeTextView.setText(event.getEventTime());
////            eventBudgetTextView.setText(event.getEventBudget());
////        }
////    }
////}
////import android.os.Bundle;
//////import android.support.v7.app.AppCompatActivity;
////import android.widget.TextView;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////public class EventDetailsActivity extends AppCompatActivity {
////
////    private TextView eventNameTextView;
////    private TextView eventLocationTextView;
////    private TextView eventDateTextView;
////    private TextView eventTimeTextView;
////    private TextView eventBudgetTextView;
////
////    private DatabaseHelper databaseHelper;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_eventdetailpage);
////
////        // Initialize views
////        eventNameTextView = findViewById(R.id.eventNameTextView);
////        eventLocationTextView = findViewById(R.id.eventLocationTextView);
////        eventDateTextView = findViewById(R.id.eventDateTextView);
////        eventTimeTextView = findViewById(R.id.eventTimeTextView);
//////        eventBudgetTextView = findViewById(R.id.ev);
////
////        // Initialize database helper
////        databaseHelper = new DatabaseHelper(this);
////
////        // Retrieve the event ID from the intent
////        long eventId = getIntent().getLongExtra("event_id", -1);
////
////        // Fetch the event details from the database
////        Event event = databaseHelper.getEventById(eventId);
////
////        if (event != null) {
////            // Update the UI with the event details
////            eventNameTextView.setText(event.getEventName());
////            eventLocationTextView.setText(event.getEventLocation());
////            eventDateTextView.setText(event.getEventDate());
////            eventTimeTextView.setText(event.getEventTime());
////            eventBudgetTextView.setText(event.getEventBudget());
////        }
////    }
////}
////import android.os.Bundle;
////import android.widget.TextView;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////public class EventDetailsActivity extends AppCompatActivity {
////    private TextView eventNameTextView;
////    private TextView eventLocationTextView;
////    private TextView eventDateTextView;
////    private TextView eventTimeTextView;
////    private TextView eventBudgetTextView;
////
////    private DatabaseHelper databaseHelper;
////    private long eventId;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_eventdetailpage);
////
////        eventNameTextView = findViewById(R.id.eventNameTextView);
////        eventLocationTextView = findViewById(R.id.eventLocationTextView);
////        eventDateTextView = findViewById(R.id.eventDateTextView);
////        eventTimeTextView = findViewById(R.id.eventTimeTextView);
////        eventBudgetTextView = findViewById(R.id.eventBudgetTextView);
////
////        databaseHelper = new DatabaseHelper(this);
////
////        // Get the eventId passed from the previous activity
////        eventId = getIntent().getLongExtra("eventId", -1);
////
////        // Retrieve the event from the database based on the eventId
////        Event event = databaseHelper.getEventById(eventId);
////
////        // Display the event details
////        if (event != null) {
////            eventNameTextView.setText(event.getEventName());
////            eventLocationTextView.setText(event.getEventLocation());
////            eventDateTextView.setText(event.getEventDate());
////            eventTimeTextView.setText(event.getEventTime());
////            eventBudgetTextView.setText(event.getEventBudget());
////        }
////    }
////
////    @Override
////    protected void onDestroy() {
////        super.onDestroy();
////        // Close the database connection
////        databaseHelper.close();
////    }
////}
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class EventDetailsActivity extends AppCompatActivity {
//    private TextView eventNameTextView;
//    private TextView eventLocationTextView;
//    private TextView eventDateTextView;
//    private TextView eventTimeTextView;
//    private TextView eventBudgetTextView;
//
//    private DatabaseHelper databaseHelper;
//    private long eventId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_eventdetailpage);
//
//        eventNameTextView = findViewById(R.id.eventNameTextView);
//        eventLocationTextView = findViewById(R.id.eventLocationTextView);
//        eventDateTextView = findViewById(R.id.eventDateTextView);
//        eventTimeTextView = findViewById(R.id.eventTimeTextView);
//        eventBudgetTextView = findViewById(R.id.eventBudgetTextView);
//
//        databaseHelper = new DatabaseHelper(this);
//
//
//// Get the eventId passed from the previous activity
//        eventId = getIntent().getLongExtra("eventId", -1);
//
//        // Retrieve the event from the database based on the eventId
//
//        Event event = databaseHelper.getEventById(eventId);
//
//
//        // Display the event details
//        if (event != null) {
//            eventNameTextView.setText(event.getEventName());
//            eventLocationTextView.setText(event.getEventLocation());
//            eventDateTextView.setText(event.getEventDate());
//            eventTimeTextView.setText(event.getEventTime());
//            eventBudgetTextView.setText(event.getEventBudget());
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Close the database connection
//        databaseHelper.close();
//    }
//}
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planahead_capstone.EventDetails;

public class EventDetailsActivity extends AppCompatActivity {
//    private TextView eventNameTextView;
//    private TextView eventLocationTextView;
//    private TextView eventDateTextView;
//    private TextView eventTimeTextView;
//    private TextView eventBudgetTextView;
//
//    private DatabaseHelper databaseHelper;
//    private int eventId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_eventdetailpage);
//
//        eventNameTextView = findViewById(R.id.eventNameTextView);
//        eventLocationTextView = findViewById(R.id.eventLocationTextView);
//        eventDateTextView = findViewById(R.id.eventDateTextView);
//        eventTimeTextView = findViewById(R.id.eventTimeTextView);
//        eventBudgetTextView = findViewById(R.id.eventBudgetTextView);
//
//        databaseHelper = new DatabaseHelper(this);
//
//        // Get the eventId passed from the previous activity
//        eventId = getIntent().getIntExtra("eventId", -1);
//
//        // Retrieve the event from the database based on the eventId
//        EventDetails event = databaseHelper.getEventDetailsById(eventId);
//
//        // Display the event details
//        if (event != null) {
//            eventNameTextView.setText(event.getEventName());
//            eventLocationTextView.setText(event.getEventLocation());
//            eventDateTextView.setText(event.getEventDate());
//            eventTimeTextView.setText(event.getEventTime());
//            eventBudgetTextView.setText(event.getEventBudget());
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Close the database connection
//        databaseHelper.close();
//    }
}
