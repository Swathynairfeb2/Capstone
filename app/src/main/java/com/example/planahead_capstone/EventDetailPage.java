
package com.example.planahead_capstone;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EventDetailPage extends AppCompatActivity {

    private ImageView addTaskImage;
    private ImageView eventInvitationImage;
    private TextView eventNameTextView;
    private TextView eventLocationTextView;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventBudgetTextView;
    private DatabaseHelper databaseHelper;
    private Integer eventid;
    private String eventId;

    UpcomingEvent event;


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

        databaseHelper = new DatabaseHelper(this);

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
             event = intent.getParcelableExtra("event");
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
    }
}
