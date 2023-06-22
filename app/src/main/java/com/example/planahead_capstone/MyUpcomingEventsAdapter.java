package com.example.planahead_capstone;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class MyUpcomingEventsAdapter extends RecyclerView.Adapter<MyUpcomingEventsAdapter.ViewHolder> {
//
//    private List<UpcomingEvent> upcomingEventList;
//
//    public MyUpcomingEventsAdapter(List<UpcomingEvent> upcomingEventList) {
//        this.upcomingEventList = upcomingEventList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        int eventIndex1 = position * 2;
//        int eventIndex2 = position * 2 + 1;
//
//        UpcomingEvent upcomingEvent1 = upcomingEventList.get(eventIndex1);
//        holder.eventLinearLayout1.setVisibility(View.VISIBLE);
//        holder.eventNameTextView1.setText(upcomingEvent1.getEventName());
//        holder.eventDateTextView1.setText(upcomingEvent1.getEventDate());
//
//        if (eventIndex2 < upcomingEventList.size()) {
//            final UpcomingEvent upcomingEvent2 = upcomingEventList.get(eventIndex2);
//            holder.eventLinearLayout2.setVisibility(View.VISIBLE);
//            holder.eventNameTextView2.setText(upcomingEvent2.getEventName());
//            holder.eventDateTextView2.setText(upcomingEvent2.getEventDate());
//
//            holder.eventLinearLayout2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), EventDetailPage.class);
//                    intent.putExtra("event", upcomingEvent2);
//                    v.getContext().startActivity(intent);
//                }
//            });
//        } else {
//            holder.eventLinearLayout2.setVisibility(View.GONE);
//        }
//
//        holder.eventLinearLayout1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), EventDetailPage.class);
//                intent.putExtra("event", upcomingEvent1);
//                v.getContext().startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return (int) Math.ceil(upcomingEventList.size() / 2.0);
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        LinearLayout eventLinearLayout1;
//        TextView eventNameTextView1;
//        TextView eventDateTextView1;
//
//        LinearLayout eventLinearLayout2;
//        TextView eventNameTextView2;
//        TextView eventDateTextView2;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            eventLinearLayout1 = itemView.findViewById(R.id.eventLinearLayout1);
//            eventNameTextView1 = itemView.findViewById(R.id.eventNameTextView);
//            eventDateTextView1 = itemView.findViewById(R.id.eventDateTextView);
//
//            eventLinearLayout2 = itemView.findViewById(R.id.eventLinearLayout2);
//            eventNameTextView2 = itemView.findViewById(R.id.eventNameTextView2);
//            eventDateTextView2 = itemView.findViewById(R.id.eventDateTextView2);
//
//            eventLinearLayout1.setOnClickListener(this);
//            eventLinearLayout2.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                int eventIndex = position * 2;
//                UpcomingEvent clickedEvent = upcomingEventList.get(eventIndex);
//
//                Intent intent = new Intent(v.getContext(), EventDetailPage.class);
//                intent.putExtra("event", clickedEvent);
//                v.getContext().startActivity(intent);
//            }
//        }
//    }
//}
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyUpcomingEventsAdapter extends RecyclerView.Adapter<MyUpcomingEventsAdapter.ViewHolder> {

    private List<UpcomingEvent> upcomingEventList;
    private Handler handler;
    private Runnable runnable;

    public MyUpcomingEventsAdapter(List<UpcomingEvent> upcomingEventList) {
        this.upcomingEventList = upcomingEventList;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateCountdownTimers();
                handler.postDelayed(this, 1000); // Update countdown every 1 second
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int eventIndex1 = position * 2;
        int eventIndex2 = position * 2 + 1;

        UpcomingEvent upcomingEvent1 = upcomingEventList.get(eventIndex1);
        holder.eventLinearLayout1.setVisibility(View.VISIBLE);
        holder.eventNameTextView1.setText(upcomingEvent1.getEventName());
        holder.eventDateTextView1.setText(upcomingEvent1.getEventDate());
        holder.eventCountdownTextView1.setText(upcomingEvent1.getCountdownText());

        if (eventIndex2 < upcomingEventList.size()) {
            final UpcomingEvent upcomingEvent2 = upcomingEventList.get(eventIndex2);
            holder.eventLinearLayout2.setVisibility(View.VISIBLE);
            holder.eventNameTextView2.setText(upcomingEvent2.getEventName());
            holder.eventDateTextView2.setText(upcomingEvent2.getEventDate());
            holder.eventCountdownTextView2.setText(upcomingEvent2.getCountdownText());

            holder.eventLinearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EventDetailPage.class);
                    intent.putExtra("event", upcomingEvent2);
                    v.getContext().startActivity(intent);
                }
            });
        } else {
            holder.eventLinearLayout2.setVisibility(View.GONE);
        }

        holder.eventLinearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetailPage.class);
                intent.putExtra("event", upcomingEvent1);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(upcomingEventList.size() / 2.0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout eventLinearLayout1;
        TextView eventNameTextView1;
        TextView eventDateTextView1;
        TextView eventCountdownTextView1;

        LinearLayout eventLinearLayout2;
        TextView eventNameTextView2;
        TextView eventDateTextView2;
        TextView eventCountdownTextView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventLinearLayout1 = itemView.findViewById(R.id.eventLinearLayout1);
            eventNameTextView1 = itemView.findViewById(R.id.eventNameTextView);
            eventDateTextView1 = itemView.findViewById(R.id.eventDateTextView);
            eventCountdownTextView1 = itemView.findViewById(R.id.countdownTextView1);

            eventLinearLayout2 = itemView.findViewById(R.id.eventLinearLayout2);
            eventNameTextView2 = itemView.findViewById(R.id.eventNameTextView2);
            eventDateTextView2 = itemView.findViewById(R.id.eventDateTextView2);
            eventCountdownTextView2 = itemView.findViewById(R.id.countdownTextView2);
        }
    }

    public void startCountdownTimer() {
        handler.postDelayed(runnable, 0);
    }

    public void stopCountdownTimer() {
        handler.removeCallbacks(runnable);
    }

//    private void updateCountdownTimers() {
//        // Get the current time
//        long currentTime = System.currentTimeMillis();
//
//        for (UpcomingEvent event : upcomingEventList) {
//            // Get the event date and time
//            String eventDateTime = event.getEventDate() + " " + event.getEventTime();
//
//            // Parse the event date and time to a Date object
//            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
//            Date eventDate = null;
//            try {
//                eventDate = format.parse(eventDateTime);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            if (eventDate != null) {
//                // Calculate the time difference between the current time and event time
//                long timeDiff = eventDate.getTime() - currentTime;
//
//                if (timeDiff > 0) {
//                    // Format the time difference as a countdown text
//                    String countdownText = formatCountdownText(timeDiff);
//
//                    // Update the countdown text for the event
//                    event.setCountdownText(countdownText);
//                } else {
//                    // Event has passed, set countdown text to "Expired"
//                    event.setCountdownText("Expired");
//                }
//            }
//        }
//
//        notifyDataSetChanged();
//    }
private void updateCountdownTimers() {
    // Get the current time
    long currentTime = System.currentTimeMillis();

    for (UpcomingEvent event : upcomingEventList) {
        // Get the event date and time
        String eventDateTime = event.getEventDate() + " " + event.getEventTime();

        // Parse the event date and time to a Date object
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
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
        long hours = TimeUnit.MILLISECONDS.toHours(timeDiff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff) % 60;

        // Create a formatted countdown text
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
