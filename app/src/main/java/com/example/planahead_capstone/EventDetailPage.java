
package com.example.planahead_capstone;
import android.content.Intent;
import android.os.Bundle;
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
    private ImageView eventInvitationImage;
    private ImageView budgetImage;
    private TextView invitationTextView;
    private TextView eventNameTextView;
    private TextView eventLocationTextView;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventBudgetTextView;
    private DatabaseHelper databaseHelper;
    private String eventId;

    UpcomingEvent event;

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
        eventInvitationImage = findViewById(R.id.invitationImageView);

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
                openTodoListPage();
            }
        });
        eventInvitationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInvitationPage();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            UpcomingEvent event = intent.getParcelableExtra("event");
            if (event != null) {
                updateEventDetails(event);
            }
        }
    }

    private void updateEventDetails(UpcomingEvent event) {
        if (event != null) {
            eventid = Integer.valueOf(event.getEventId());

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

    private void openTodoListPage() {
        if (eventid != null) {
            Intent intent = new Intent(this, TodoListActivity.class);
            intent.putExtra("eventId", eventid);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "eventId is null", Toast.LENGTH_SHORT).show();
        }
    }
    private void openInvitationPage() {
        if (event != null) {
            Intent intent = new Intent(this,InvitationActivity.class);
            intent.putExtra("event", event);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "eventId is null", Toast.LENGTH_SHORT).show();
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
