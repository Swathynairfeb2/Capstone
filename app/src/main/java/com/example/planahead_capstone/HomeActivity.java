package com.example.planahead_capstone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.planahead_capstone.EventCreationActivity;
import com.example.planahead_capstone.Fragment1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Button menu_my_events;
    private ViewPager viewPager;
    private List<UpcomingEvent> upcomingEvents;

    // Add the missing constant values
    private static final String COLUMN_EVENT_NAME = "event_name";
    private static final String COLUMN_EVENT_DATE = "event_date";
    private static final String TABLE_EVENTS = "events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize ViewPager
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);

        // Get upcoming events from the database
        upcomingEvents = getUpcomingEvents();

        // Display upcoming events
        displayUpcomingEvents();
    }

    private void setupViewPager(ViewPager viewPager) {
        // Create and set up your adapter for ViewPager
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "Fragment 1");
        adapter.addFragment(new Fragment2(), "Fragment 2");
        adapter.addFragment(new Fragment3(), "Fragment 3");
        viewPager.setAdapter(adapter);
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
        // Display the upcoming events in a desired way (e.g., ListView, RecyclerView, TextView)
        MyUpcomingEventsAdapter adapter = new MyUpcomingEventsAdapter(upcomingEvents);
        RecyclerView recyclerView = findViewById(R.id.upcomingEventsRecyclerView);
        recyclerView.setAdapter(adapter);
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
                            Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            intent = new Intent(HomeActivity.this, CategoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            intent = new Intent(HomeActivity.this, EventCreationActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_account:
                            // Handle the my account action
                            Toast.makeText(HomeActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    return true;
                }
            };
}
