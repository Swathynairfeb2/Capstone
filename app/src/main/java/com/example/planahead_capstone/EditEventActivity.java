package com.example.planahead_capstone;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planahead_capstone.DatabaseHelper;
import com.example.planahead_capstone.UpcomingEvent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Locale;

public class EditEventActivity extends AppCompatActivity {

    private EditText eventNameEditText;
    private EditText eventLocationEditText;
    private EditText eventDateEditText;
    private EditText eventTimeEditText;
    private EditText eventBudgetEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;

    private UpcomingEvent event;

    private Calendar calendar;
    private int year, month, day, hour, minute;

    private DatePickerDialog.OnDateSetListener datePickerListener;
    private TimePickerDialog.OnTimeSetListener timePickerListener;
    private ImageView imageViewDateIcon;
    private ImageView imageViewTimeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_edit);
        databaseHelper = new DatabaseHelper(this);

        eventNameEditText = findViewById(R.id.editTextEventName);
        eventLocationEditText = findViewById(R.id.editTextEventLocation);
        eventDateEditText = findViewById(R.id.editTextEventDate);
        eventTimeEditText = findViewById(R.id.editTextEventTime);
        eventBudgetEditText = findViewById(R.id.editTextEventBudget);
        saveButton = findViewById(R.id.buttonUpdateEvent);
        imageViewDateIcon = findViewById(R.id.imageViewDateIcon);
        imageViewTimeIcon = findViewById(R.id.imageViewTimeIcon);

        event = getIntent().getParcelableExtra("event");

        if (event != null) {
            eventNameEditText.setText(event.getEventName());
            eventLocationEditText.setText(event.getEventLocation());
            eventDateEditText.setText(event.getEventDate());
            eventTimeEditText.setText(event.getEventTime());
            eventBudgetEditText.setText(event.getEventBudget());
        }

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        // Set the event date when the date EditText is clicked
        //eventDateEditText.setOnClickListener(new View.OnClickListener() {
//        imageViewDateIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        EditEventActivity.this,
//                        datePickerListener,
//                        year, month, day);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//                datePickerDialog.show();
//            }
//
//        });
//
//
//        // Set the event time when the time EditText is clicked
//        //eventTimeEditText.setOnClickListener(new View.OnClickListener() {
//        imageViewTimeIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(
//                        EditEventActivity.this,
//                        timePickerListener,
//                        hour, minute, false);
//                timePickerDialog.show();
//            }
//        });
        imageViewDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        imageViewTimeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        // Set the selected date in the event date EditText
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                eventDateEditText.setText(String.format("%02d/%02d/%04d", day, month + 1, year));
            }
        };

        // Set the selected time in the event time EditText
        timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                eventTimeEditText.setText(String.format("%02d:%02d", hour, minute));
            }
        };

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEventChanges();
            }
        });

        // Initialize the date picker listener
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            //public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                updateDate(year, month, dayOfMonth);
            }
        };

        // Initialize the time picker listener
        timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar selectedTime = Calendar.getInstance();
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedTime.set(Calendar.MINUTE, minute);

                Calendar currentTime = Calendar.getInstance();
                if (selectedTime.before(currentTime)) {
                    // Selected time is before the current time, show an error message
                    Toast.makeText(EditEventActivity.this, "Please select a valid time", Toast.LENGTH_SHORT).show();
                } else {
                    updateTime(hourOfDay, minute);
                }
            }
        };

    }



    private void saveEventChanges() {
        String eventName = eventNameEditText.getText().toString();
        String eventLocation = eventLocationEditText.getText().toString();
        String eventDate = eventDateEditText.getText().toString();
        String eventTime = eventTimeEditText.getText().toString();
        String eventBudget = eventBudgetEditText.getText().toString();
        // Convert the event ID to an int
        int eventId = Integer.parseInt(event.getEventId());

        // Update the event in the database
        databaseHelper.updateEvent(eventId, eventName, eventLocation, eventDate, eventTime, eventBudget);

        // Display the Toast
        Toast toast = Toast.makeText(EditEventActivity.this, "Event updated successfully", Toast.LENGTH_SHORT);

        // Apply the custom style
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.custom_toast_background);
        TextView toastText = toastView.findViewById(android.R.id.message);
        toastText.setTextColor(getResources().getColor(R.color.white));

        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();


        // Redirect to the UpcomingEventsActivity
        Intent intent = new Intent(EditEventActivity.this, EventsActivity.class);
        startActivity(intent);
        finish();

    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set the minimum date to today
        datePickerDialog.show();

    }

    private void updateDate(int year, int month, int dayOfMonth) {
        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", month + 1, dayOfMonth, year);
        eventDateEditText.setText(selectedDate);
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timePickerListener, hourOfDay, minute, false);
        timePickerDialog.show();
    }

    private void updateTime(int hourOfDay, int minute) {
        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
        eventTimeEditText.setText(selectedTime);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Intent intent;

                    switch (item.getItemId()) {
                        case R.id.menu_home:
                            // Handle the home action
                            intent = new Intent(EditEventActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            intent = new Intent(EditEventActivity.this, CategoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            intent = new Intent(EditEventActivity.this, EventsActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_account:
                            // Handle the my account action
                            Toast.makeText(EditEventActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    return true;
                }
            };
}
