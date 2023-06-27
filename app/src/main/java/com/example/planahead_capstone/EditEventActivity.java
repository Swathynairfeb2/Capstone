package com.example.planahead_capstone;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planahead_capstone.DatabaseHelper;
import com.example.planahead_capstone.UpcomingEvent;

import java.util.Calendar;

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
        eventDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditEventActivity.this,
                        datePickerListener,
                        year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        // Set the event time when the time EditText is clicked
        eventTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditEventActivity.this,
                        timePickerListener,
                        hour, minute, false);
                timePickerDialog.show();
            }
        });

        // Set the selected date in the event date EditText
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                eventDateEditText.setText(String.format("%02d-%02d-%04d", day, month + 1, year));
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

        Toast.makeText(EditEventActivity.this, "Event updated successfully", Toast.LENGTH_SHORT).show();
    }

}
