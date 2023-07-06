package com.example.planahead_capstone;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView eventsHeadingTextView;
    private RelativeLayout dynamicImageLayout;
    private ImageView dynamicImageView;
    private TextView dynamicImageTextView;
    private List<EventDetails> upcomingEvents;
    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private EventDetails todaysEvent; // Track today's event
    // Declare the ConfettiView
    private KonfettiView confettiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the currentDateImageView in the layout
        ImageView currentDateImageView = findViewById(R.id.currentDateImageView);
        TextView monthTextView = findViewById(R.id.monthTextView);
        TextView dayTextView = findViewById(R.id.dayTextView);

        // Get the current date
        Calendar currentDatehome = Calendar.getInstance();
        int monthhome = currentDatehome.get(Calendar.MONTH);
        int dayOfMonth = currentDatehome.get(Calendar.DAY_OF_MONTH);

        // Get the corresponding month name for the current month
        String[] monthNames = new DateFormatSymbols().getShortMonths();
        String monthName = monthNames[monthhome];

        // Format the date as "MON dd"
        String formattedDate = String.format(Locale.getDefault(), "%s %02d", monthName, dayOfMonth);

        // Set the formatted date as the content description of the currentDateImageView
        currentDateImageView.setContentDescription(formattedDate);
        monthTextView.setText(monthName);
        dayTextView.setText(String.valueOf(dayOfMonth));

        eventRecyclerView = findViewById(R.id.eventRecyclerView);
        eventAdapter = new EventAdapter();
        eventRecyclerView.setAdapter(eventAdapter);

        eventRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));




        //Initialize ViewPager
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        // Initialize the ConfettiView in onCreate or onViewCreated
        //   confettiView = findViewById(R.id.confettiView);

        eventsHeadingTextView = findViewById(R.id.eventsHeadingTextView);
        dynamicImageLayout = findViewById(R.id.dynamicImageLayout);
        dynamicImageView = findViewById(R.id.dynamicImageView);
        dynamicImageTextView = findViewById(R.id.dynamicImageTextView);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);

        upcomingEvents = getUpcomingEvents();

        List<EventDetails> todayAndTomorrowEvents = getEventsForTodayAndTomorrow();

        updateUI(todayAndTomorrowEvents);
        getUpcomingEvents();

        // Find today's event
        for (EventDetails event : upcomingEvents) {
            String eventDate = event.getEventDate();
            String[] dateParts = eventDate.split("/");
            if (dateParts.length >= 3) {
                int eventYear = Integer.parseInt(dateParts[2]);
                int eventMonth = Integer.parseInt(dateParts[0]);
                int eventDay = Integer.parseInt(dateParts[1]);

                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH) + 1;
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                if (eventYear == year && eventMonth == month && eventDay == day) {
                    todaysEvent = event;
                    break;
                }
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "Fragment 1");
        adapter.addFragment(new Fragment2(), "Fragment 2");
        adapter.addFragment(new Fragment3(), "Fragment 3");
        viewPager.setAdapter(adapter);
    }


private List<EventDetails> getUpcomingEvents() {
    List<EventDetails> upcomingEvents = new ArrayList<>();

    DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
    List<EventDetails> allEvents = databaseHelper.getUpcomingEvents();

    // Get the current date and time
    Calendar currentDateTime = Calendar.getInstance();

    // Iterate through all events
    for (EventDetails event : allEvents) {
        String eventDateTime = event.getEventDate() + " " + event.getEventTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());

        try {
            // Parse the event date and time
            Date eventDate = dateFormat.parse(eventDateTime);

            // Compare the event date and time with the current date and time
            if (eventDate != null && eventDate.after(currentDateTime.getTime())) {
                upcomingEvents.add(event);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    return upcomingEvents;
}

    private List<EventDetails> getEventsForTodayAndTomorrow() {
        List<EventDetails> events = new ArrayList<>();

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1;
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        for (EventDetails event : upcomingEvents) {
            String eventDate = event.getEventDate();
            String[] dateParts = eventDate.split("/");
            if (dateParts.length >= 3) {
                int eventYear = Integer.parseInt(dateParts[2]);
                int eventMonth = Integer.parseInt(dateParts[0]);
                int eventDay = Integer.parseInt(dateParts[1]);

                if ((eventYear == year && eventMonth == month && eventDay == day) ||
                        (eventYear == year && eventMonth == month && eventDay == day + 1)) {
                    events.add(event);
                }
            }
        }

        return events;
    }

    private void updateUI(List<EventDetails> events) {
        boolean hasEvents = !events.isEmpty();
        boolean hasTodayEvents = checkIfTodayEventsExist();

        if (hasEvents) {
            dynamicImageLayout.setVisibility(View.VISIBLE);
            dynamicImageView.setVisibility(View.GONE);
            dynamicImageTextView.setVisibility(View.GONE);

            eventRecyclerView.setVisibility(View.VISIBLE);
            eventAdapter.setEvents(events);
        } else {
            dynamicImageLayout.setVisibility(View.GONE);
            eventRecyclerView.setVisibility(View.GONE);
        }
    }

    private boolean checkIfTodayEventsExist() {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1;
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        for (EventDetails event : upcomingEvents) {
            String eventDate = event.getEventDate();
            String[] dateParts = eventDate.split("/");
            if (dateParts.length >= 3) {
                int eventYear = Integer.parseInt(dateParts[2]);
                int eventMonth = Integer.parseInt(dateParts[0]);
                int eventDay = Integer.parseInt(dateParts[1]);

                if (eventYear == year && eventMonth == month && eventDay == day) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Set the events in the eventAdapter
        eventAdapter.setEvents(upcomingEvents);

        // Start the countdown timer in the eventAdapter
        eventAdapter.startCountdownTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Stop the countdown timer in the eventAdapter
        eventAdapter.stopCountdownTimer();
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
                            //   checkIfTodayEventsExist();
                            // getEventsForTodayAndTomorrow();
                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            intent = new Intent(HomeActivity.this, CategoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            intent = new Intent(HomeActivity.this, EventsActivity.class);
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
