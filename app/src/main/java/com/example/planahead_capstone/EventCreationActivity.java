package com.example.planahead_capstone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class EventCreationActivity extends AppCompatActivity {

    private EditText editTextEventName;
    private EditText editTextEventLocation;
    private TextView textViewEventDate;
    private TextView textViewEventTime;
    private EditText editTextEventBudget;
    private Button buttonCreateEvent;

    private DatePickerDialog.OnDateSetListener datePickerListener;
    private TimePickerDialog.OnTimeSetListener timePickerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_creation);

        // Initialize views
        editTextEventName = findViewById(R.id.editTextEventName);
        editTextEventLocation = findViewById(R.id.editTextEventLocation);
        textViewEventDate = findViewById(R.id.textViewEventDate);
        textViewEventTime = findViewById(R.id.textViewEventTime);
        editTextEventBudget = findViewById(R.id.editTextEventBudget);
        buttonCreateEvent = findViewById(R.id.buttonCreateEvent);

        // Set click listeners for the picker icons
        ImageView imageViewLocationIcon = findViewById(R.id.imageViewLocationIcon);
        ImageView imageViewDateIcon = findViewById(R.id.imageViewDateIcon);
        ImageView imageViewTimeIcon = findViewById(R.id.imageViewTimeIcon);


        imageViewLocationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement location picker logic
            }
        });

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


        // Set click listener for Create Event button
        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input values
                String eventName = editTextEventName.getText().toString();
                String eventLocation = editTextEventLocation.getText().toString();
                String eventDate = textViewEventDate.getText().toString();
                String eventTime = textViewEventTime.getText().toString();
                String eventBudget = editTextEventBudget.getText().toString();

                // Save the event details to the database
                boolean eventSaved = saveEventToDatabase(eventName, eventLocation, eventDate, eventTime, eventBudget);

                if (eventSaved) {
                    // Show a toast message to indicate successful event creation
                    Toast.makeText(EventCreationActivity.this, "Event created: " + eventName, Toast.LENGTH_SHORT).show();

                    // Finish the activity and go back to the previous screen
                    finish();
                } else {
                    // Show a toast message indicating a failure in saving the event
                    Toast.makeText(EventCreationActivity.this, "Failed to save the event", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize the date picker listener
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                updateDate(year, month, dayOfMonth);
            }
        };

        // Initialize the time picker listener
        timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                updateTime(hourOfDay, minute);
            }
        };
    }

    private boolean saveEventToDatabase(String eventName, String eventLocation, String eventDate, String eventTime, String eventBudget) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("event_name", eventName);
        values.put("event_location", eventLocation);
        values.put("event_date", eventDate);
        values.put("event_time", eventTime);
        values.put("event_budget", eventBudget);

        long result = db.insert("events", null, values);
        db.close();

        return result != -1;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void updateDate(int year, int month, int dayOfMonth) {
        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", month + 1, dayOfMonth, year);
        textViewEventDate.setText(selectedDate);
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
        textViewEventTime.setText(selectedTime);
    }
}
