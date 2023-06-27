package com.example.planahead_capstone;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;
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

    private Spinner spinnerEventCategory;
    private DatabaseHelper databaseHelper;
    private List<Category> categoryList;
    private ArrayAdapter<Category> categoryAdapter;
    private int selectedCategoryId = -1; // Default value for no category selected

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
        spinnerEventCategory = findViewById(R.id.spinnerEventCategory);
// Retrieve the passed data from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            String eventName = intent.getStringExtra("eventName");
            String eventLocation = intent.getStringExtra("eventLocation");
            String eventDate = intent.getStringExtra("eventDate");
            String eventTime = intent.getStringExtra("eventTime");
            String eventBudget = intent.getStringExtra("eventBudget");

            // Set the retrieved data to the corresponding EditText fields
            editTextEventName.setText(eventName);
            editTextEventLocation.setText(eventLocation);
            textViewEventDate.setText(eventDate);
            textViewEventTime.setText(eventTime);
            editTextEventBudget.setText(eventBudget);
        }

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

                // Validate the input fields
                if (eventName.isEmpty()) {
                Toast toast=    Toast.makeText(EventCreationActivity.this, "Please enter event name", Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.custom_toast_background);
                    TextView toastText = toastView.findViewById(android.R.id.message);
                    toastText.setTextColor(getResources().getColor(R.color.white));

                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();


                    return;
                }

                if (eventLocation.isEmpty()) {
                    Toast toast=     Toast.makeText(EventCreationActivity.this, "Please enter event location", Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.custom_toast_background);
                    TextView toastText = toastView.findViewById(android.R.id.message);
                    toastText.setTextColor(getResources().getColor(R.color.white));

                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();

                    return;
                }

                if (eventDate.isEmpty()) {
                    Toast toast= Toast.makeText(EventCreationActivity.this, "Please select event date", Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.custom_toast_background);
                    TextView toastText = toastView.findViewById(android.R.id.message);
                    toastText.setTextColor(getResources().getColor(R.color.white));

                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();

                    return;
                }

                if (eventTime.isEmpty()) {
                    Toast toast= Toast.makeText(EventCreationActivity.this, "Please select event time", Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.custom_toast_background);
                    TextView toastText = toastView.findViewById(android.R.id.message);
                    toastText.setTextColor(getResources().getColor(R.color.white));

                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();

                    return;
                }

                if (eventBudget.isEmpty()) {
                    Toast toast= Toast.makeText(EventCreationActivity.this, "Please enter event budget", Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.custom_toast_background);
                    TextView toastText = toastView.findViewById(android.R.id.message);
                    toastText.setTextColor(getResources().getColor(R.color.white));

                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();

                    return;
                }



                // Save the event details to the database
                boolean eventSaved = saveEventToDatabase(eventName, eventLocation, eventDate, eventTime, eventBudget);

                if (eventSaved) {
//                    // Show a toast message to indicate successful event creation
//                    Toast.makeText(EventCreationActivity.this, "Event created: " + eventName, Toast.LENGTH_SHORT).show();
                    // Display the Toast
                    Toast toast = Toast.makeText(EventCreationActivity.this, "Event created: " + eventName, Toast.LENGTH_SHORT);

            // Apply the custom style
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.custom_toast_background);
                    TextView toastText = toastView.findViewById(android.R.id.message);
                    toastText.setTextColor(getResources().getColor(R.color.white));

                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();

                    // Finish the activity and go back to the previous screen
                    finish();
                } else {
                    // Show a toast message indicating a failure in saving the event
                    Toast toast=  Toast.makeText(EventCreationActivity.this, "Failed to save the event", Toast.LENGTH_SHORT);
                    // Apply the custom style
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.custom_toast_background);
                    TextView toastText = toastView.findViewById(android.R.id.message);
                    toastText.setTextColor(getResources().getColor(R.color.white));

                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
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

        // Set a listener to handle the selected category
        spinnerEventCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = categoryList.get(position);
                selectedCategoryId = selectedCategory.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategoryId = -1; // Reset the selected category ID
            }
        });

        // Initialize the database helper and load categories
        databaseHelper = new DatabaseHelper(this);
        loadCategories();

        // Get the category ID passed from the CategoryActivity
        int categoryId = getIntent().getIntExtra("categoryId", -1);

        // Select the category in the spinner based on the category ID
        if (categoryId != -1) {
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == categoryId) {
                    spinnerEventCategory.setSelection(i);
                    break;
                }
            }
        }
    }

    private boolean saveEventToDatabase(String eventName, String eventLocation, String eventDate, String eventTime, String eventBudget) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Insert event details into the events table
        ContentValues values = new ContentValues();
        values.put("event_name", eventName);
        values.put("event_location", eventLocation);
        values.put("event_date", eventDate);
        values.put("event_time", eventTime);
        values.put("event_budget", eventBudget);
        long eventId = db.insert("events", null, values);

        // Insert the event-category mapping into the event_category table
        if (eventId != -1) {
            if (selectedCategoryId != -1) {
                ContentValues mappingValues = new ContentValues();
                mappingValues.put("event_id", eventId);
                mappingValues.put("category_id", selectedCategoryId);
                long mappingResult = db.insert("event_category", null, mappingValues);
                if (mappingResult == -1) {
                    // Failed to insert event-category mapping, delete the event from the events table
                    db.delete("events", "id=?", new String[]{String.valueOf(eventId)});
                    db.close();
                    return false;
                }
            }
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
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

    private void loadCategories() {
        categoryList = databaseHelper.getAllCategories();
        categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinnerEventCategory.setAdapter(categoryAdapter);
    }
}
