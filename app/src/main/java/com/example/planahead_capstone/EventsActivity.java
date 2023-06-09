package com.example.planahead_capstone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {
    private List<UpcomingEvent> upcomingEvents;

    // Add the missing constant values
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_DATE = "event_date";
    private static final String TABLE_EVENTS = "events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_upcoming_event);

        // Get upcoming events from the database
        upcomingEvents = getUpcomingEvents();

        // Display upcoming events
        displayUpcomingEvents();
    }

    private List<UpcomingEvent> getUpcomingEvents() {
        List<UpcomingEvent> upcomingEvents = new ArrayList<>();

        // Retrieve events from the database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Define the columns to retrieve from the events table
        String[] projection = {
                COLUMN_EVENT_NAME,
                COLUMN_EVENT_DATE
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
            String eventName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_NAME));
            String eventDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DATE));
            UpcomingEvent event = new UpcomingEvent(eventName, eventDate, R.drawable.smart_view1);
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
}