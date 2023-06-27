package com.example.planahead_capstone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {
    private List<UpcomingEvent> upcomingEvents;
    private MyUpcomingEventsAdapter adapter;

    // Add the missing constant values
    private static final String COLUMN_EVENT_ID = "id";
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_LOCATION = "event_location";
    private static final String COLUMN_EVENT_DATE = "event_date";
    private static final String COLUMN_EVENT_TIME = "event_time";
    private static final String COLUMN_EVENT_BUDGET = "event_budget";
    private static final String TABLE_EVENTS = "events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_upcoming_event);

        // Get upcoming events from the database
        upcomingEvents = getUpcomingEvents();

        // Display upcoming events
        displayUpcomingEvents();
        // Initialize the adapter
        adapter = new MyUpcomingEventsAdapter(upcomingEvents);
        // Set the adapter and layout manager to the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.upcomingEventsRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<UpcomingEvent> getUpcomingEvents() {
        List<UpcomingEvent> upcomingEvents = new ArrayList<>();

        // Retrieve events from the database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Define the columns to retrieve from the events table
        String[] projection = {
                COLUMN_EVENT_ID,
                COLUMN_EVENT_NAME,
                COLUMN_EVENT_LOCATION,
                COLUMN_EVENT_DATE,
                COLUMN_EVENT_TIME,
                COLUMN_EVENT_BUDGET

        };

        // Query the events table
        Cursor cursor = db.query(
                TABLE_EVENTS,   // Table name
                projection,     // Columns to retrieve
                null,           // WHERE clause
                null,           // WHERE clause arguments
                null,           // GROUP BY
                null,           // HAVING
                null            // ORDER BY
        );

        // Iterate over the cursor and create UpcomingEvent objects
        while (cursor.moveToNext()) {
            String eventId=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_ID));
            String eventName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_NAME));
            String eventLocation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_LOCATION));
            String eventDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DATE));
            String eventTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_TIME));
            String eventBudget = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_BUDGET));
            UpcomingEvent event = new UpcomingEvent(eventId,eventName,eventLocation, eventDate,eventTime,eventBudget, R.drawable.group_1);
            // Create an UpcomingEvent object with only name and date

            upcomingEvents.add(event);
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return upcomingEvents;
    }

    private void displayUpcomingEvents() {
        // Display the upcoming events in a desired way (e.g., RecyclerView, ListView, TextView)
        MyUpcomingEventsAdapter adapter = new MyUpcomingEventsAdapter(upcomingEvents);
        RecyclerView recyclerView = findViewById(R.id.upcomingEventsRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startCountdownTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopCountdownTimer();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Fragment selectedFragment = null;
                    Intent intent;

                    switch (item.getItemId()) {
                        case R.id.menu_home:
                            // Handle the home action
                            intent = new Intent(EventsActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            intent = new Intent(EventsActivity.this, CategoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            intent = new Intent(EventsActivity.this, EventsActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_account:
                            // Handle the my account action
                            Toast.makeText(EventsActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    return true;
                }
            };
}

