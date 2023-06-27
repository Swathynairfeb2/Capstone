package com.example.planahead_capstone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        Intent intent = getIntent();
        if (intent != null) {
            UpcomingEvent event = intent.getParcelableExtra("event");
            if (event != null) {
                updateEventDetails(event);
                eventId = event.getEventId();
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

//    private void deleteEvent() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // Inflate the custom dialog layout
//        View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete_event, null);
//        builder.setView(dialogView);
//
//        // Find and customize the views in the custom layout
//        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
//        TextView messageTextView = dialogView.findViewById(R.id.dialog_message);
//        titleTextView.setText("Delete Event");
//        messageTextView.setText("Are you sure you want to delete this event?");
//
//        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                databaseHelper.deleteEvent(eventId);
//
//                startActivity(intent);
//                finish();
//                //  finish(); // Finish the activity after deleting the event
//            }
//        });
//        builder.setNegativeButton("Cancel", null);
//
//        // Show the customized dialog
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//

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

    private void openTodoListPage() {
        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
    }

    private void openAddBudgetPage() {
        Intent intent = new Intent(this, BudgetListActivity.class);
        startActivity(intent);
    }

}
