package com.example.planahead_capstone;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class EventDetailPage extends AppCompatActivity {

    private ImageView addTaskImage;
    private ImageView budgetImage;
    private TextView invitationTextView;
    private TextView eventNameTextView;
    private TextView eventLocationTextView;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventBudgetTextView;
    private DatabaseHelper databaseHelper;
    private String eventId;
    private int eventid;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetailpage);

        addTaskImage = findViewById(R.id.toDoImageView);
        eventNameTextView = findViewById(R.id.eventNameTextView);
        eventLocationTextView = findViewById(R.id.eventLocationTextView);
        eventDateTextView = findViewById(R.id.eventDateTextView);
        eventTimeTextView = findViewById(R.id.eventTimeTextView);
        eventBudgetTextView = findViewById(R.id.eventBudgetTextView);
        budgetImage=findViewById(R.id.budgetImageView);
        databaseHelper = new DatabaseHelper(this);
        eventid = Integer.parseInt(eventId);
        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);

        budgetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddBudgetPage();
            }
        });
        ImageView eventOptionImage = findViewById(R.id.eventoption);
        eventOptionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

        addTaskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openTodoListPage();
            }

        });
 



        Intent intent = getIntent();
        if (intent != null) {
            UpcomingEvent event = intent.getParcelableExtra("event");
            if (event != null) {
                updateEventDetails(event);
                eventId = event.getEventId();
               // eventId = Integer.valueOf(event.getEventId());
            }
        }



        Intent intent1 = getIntent();
        if (intent1 != null) {
            String eventId = intent1.getStringExtra("eventId"); // Retrieve the eventId as a String extra
            EventDetails event1 = intent1.getParcelableExtra("events"); // Retrieve the entire EventDetails object as a Parcelable extra (optional)

            if (event1 != null) {
                // Perform necessary operations with the EventDetails object
                updateEventDetails1(event1);
                eventId = event1.getEventId();
            }
        }
    }

    private void updateEventDetails(UpcomingEvent event) {
        if (event != null) {
            String eventName = event.getEventName();
            String eventLocation = event.getEventLocation();
            String eventDate = event.getEventDate();
            String eventTime = event.getEventTime();
            String eventBudget = event.getEventBudget();

            eventNameTextView.setText(eventName);
            eventLocationTextView.setText(eventLocation);
            eventDateTextView.setText(eventDate);
            eventTimeTextView.setText(eventTime);
            eventBudgetTextView.setText(eventBudget);



            }


        }


    private void updateEventDetails1(EventDetails event1) {
        if (event1 != null) {
            String eventName = event1.getEventName();
            String eventLocation = event1.getEventLocation();
            String eventDate = event1.getEventDate();
            String eventTime = event1.getEventTime();
            String eventBudget = event1.getEventBudget();

            eventNameTextView.setText(eventName);
            eventLocationTextView.setText(eventLocation);
            eventDateTextView.setText(eventDate);
            eventTimeTextView.setText(eventTime);
            eventBudgetTextView.setText(eventBudget);



            // Get the current date
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentMonth = calendar.get(Calendar.MONTH) + 1; // Adding 1 because Calendar.MONTH starts from 0
            int currentYear = calendar.get(Calendar.YEAR);

            // Compare the event date with the current date
            String[] dateParts = eventDate.split("/");
            int eventDay = Integer.parseInt(dateParts[1]);
            int eventMonth = Integer.parseInt(dateParts[0]);
            int eventYear = Integer.parseInt(dateParts[2]);

            if (eventDay == currentDay && eventMonth == currentMonth && eventYear == currentYear) {
                // Add confetti animation here
                // You can use a library or implement your own confetti animation logic
                triggerConfettiAnimation();

            }


        }
    }


private void triggerConfettiAnimation() {
    KonfettiView konfettiView = findViewById(R.id.confettiView);
    konfettiView.build()
            .addColors(Color.parseColor("#4E376B"), Color.parseColor("#FF018786"),Color.parseColor("#FF03DAC5"), Color.parseColor("#F78B64"), Color.parseColor("#3E2B59"))
            .setDirection(0, 359)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(new Size(12, 5))
            .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
            .streamFor(300, 5000L);
}

    private void showPopupWindow(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_menu_layout, null);

        // Set up the PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setElevation(10);

        // Set up click listeners for popup window items
        TextView option1TextView = popupView.findViewById(R.id.editOptionTextView);
        TextView option2TextView = popupView.findViewById(R.id.deleteOptionTextView);

        option1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditEventPage();
                popupWindow.dismiss();
            }
        });

        option2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
                popupWindow.dismiss();
            }
        });

        // Calculate the x and y coordinates for the PopupWindow
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int anchorViewX = location[0];
        int anchorViewY = location[1];

        // Adjust the x and y coordinates to position the PopupWindow near the clicked image
        int offsetX = anchorView.getWidth() / 2;
        int offsetY = anchorView.getHeight() / 2;
        int popupX = anchorViewX - offsetX;
        int popupY = anchorViewY - offsetY;

        // Show the PopupWindow
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, popupX, popupY);
    }

        private void openEditEventPage() {
            Intent intent = getIntent();
            if (intent != null) {
                UpcomingEvent event = intent.getParcelableExtra("event");
                if (event != null) {
                    Intent editIntent = new Intent(this, EditEventActivity.class);
                    editIntent.putExtra("event", event);
                    startActivity(editIntent);
                }
            }
        }

    private void deleteEvent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustomStyle);

        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_event, null);
        builder.setView(dialogView);

        // Find and customize the views in the custom layout
        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
        TextView messageTextView = dialogView.findViewById(R.id.dialog_message);

        titleTextView.setText("Delete Event");
        messageTextView.setText("Are you sure you want to delete this event?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteEvent(eventId);
                Intent intent = new Intent(EventDetailPage.this, EventsActivity.class);
                startActivity(intent);
                finish();
                //  finish(); // Finish the activity after deleting the event
            }
        });
        builder.setNegativeButton("Cancel", null);

        // Show the customized dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private void openAddBudgetPage() {
        Intent intent = new Intent(this, BudgetListActivity.class);
        startActivity(intent);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Intent intent;

                    switch (item.getItemId()) {
                        case R.id.menu_home:
                            // Handle the home action
                            intent = new Intent(EventDetailPage.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            intent = new Intent(EventDetailPage.this, CategoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            intent = new Intent(EventDetailPage.this, EventsActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_account:
                            // Handle the my account action
                            Toast.makeText(EventDetailPage.this, "My Account", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    return true;
                }
            };
}
