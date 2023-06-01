package com.example.planahead_capstone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.text.SimpleDateFormat;
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

                // TODO: Validate input values and save the event to the database or perform desired actions

                // Show a toast message to indicate successful event creation
                Toast.makeText(EventCreationActivity.this, "Event created: " + eventName, Toast.LENGTH_SHORT).show();

                // Finish the activity and go back to the previous screen
                finish();
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

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timePickerListener, hour, minute, false);
        timePickerDialog.show();
    }

    private void updateDate(int year, int month, int day) {
        String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", month + 1, day, year);
        textViewEventDate.setText(formattedDate);
    }

    private void updateTime(int hour, int minute) {
        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
        textViewEventTime.setText(formattedTime);
    }
}
