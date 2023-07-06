package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<EventDetails> events;
    private Handler handler;
    private Runnable runnable;


    public void setEvents(List<EventDetails> events) {
        this.events = events;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateCountdownTimers();
                handler.postDelayed(this, 1000); // Update countdown every 1 second
            }
        };
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_home_upcoming, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        EventDetails event = events.get(position);
        holder.bind(event);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked event
                EventDetails clickedEvent = events.get(holder.getAdapterPosition());

                // Navigate to the event details page
                Intent intent1 = new Intent(v.getContext(), EventDetailPage.class);
                intent1.putExtra("eventId", clickedEvent.getEventId()); // Pass eventId as a String extra
                intent1.putExtra("events", clickedEvent); // Pass the entire EventDetails object as a Parcelable extra (optional)
                v.getContext().startActivity(intent1);


            }
        });
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private ImageView eventImageView;
        private TextView eventNameTextView;
        private TextView countdownTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.eventImageView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
            countdownTextView = itemView.findViewById(R.id.countdownTextView);
        }

        public void bind(EventDetails event) {
            eventImageView.setImageResource(R.drawable.home_event);
            eventNameTextView.setText(event.getEventName());
            countdownTextView.setText(event.getCountdownText());
        }
    }


public void startCountdownTimer() {
    if (handler != null) {
        handler.postDelayed(runnable, 0);
    }
}

    public void stopCountdownTimer() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
    private void updateCountdownTimers() {
        // Get the current time
        long currentTime = System.currentTimeMillis();

        for (EventDetails event : events) {
            // Get the event date and time
            String eventDateTime = event.getEventDate() + " " + event.getEventTime();

            // Parse the event date and time to a Date object
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());

            Date eventDate = null;
            try {
                eventDate = format.parse(eventDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (eventDate != null) {
                // Calculate the time difference between the current time and event time
                long timeDiff = eventDate.getTime() - currentTime;

                if (timeDiff > 0) {
                    // Format the time difference as a countdown text
                    String countdownText = formatCountdownText(timeDiff);

                    // Update the countdown text for the event
                    event.setCountdownText(countdownText);
                } else {
                    // Event has passed, set countdown text to "Expired"
                    event.setCountdownText("Expired");
                }
            }
        }

        notifyDataSetChanged();
    }

    private String formatCountdownText(long timeDiff) {
        // Convert the time difference to days, hours, minutes, and seconds
        long days = TimeUnit.MILLISECONDS.toDays(timeDiff);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDiff) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff) % 60;

        // Create a formatted countdown text
        return String.format(Locale.getDefault(), "%d:%02d:%02d:%02d",days,  hours, minutes, seconds);
    }


}

