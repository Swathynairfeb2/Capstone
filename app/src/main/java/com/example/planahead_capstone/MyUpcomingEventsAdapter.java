//package com.example.planahead_capstone;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.concurrent.TimeUnit;
//
//public class MyUpcomingEventsAdapter extends RecyclerView.Adapter<MyUpcomingEventsAdapter.ViewHolder> {
//
//    private List<UpcomingEvent> upcomingEventList;
//    private Handler handler;
//    private Runnable runnable;
//
//    public MyUpcomingEventsAdapter(List<UpcomingEvent> upcomingEventList) {
//        this.upcomingEventList = upcomingEventList;
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                updateCountdownTimers();
//                handler.postDelayed(this, 1000); // Update countdown every 1 second
//            }
//        };
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
//        holder.eventRelativeLayout1.setVisibility(View.VISIBLE);
//        holder.eventNameTextView1.setText(upcomingEvent1.getEventName());
//        holder.eventDateTextView1.setText(upcomingEvent1.getEventDate());
//        holder.countdownTextView1.setText(upcomingEvent1.getCountdownText());
//
//        if (eventIndex2 < upcomingEventList.size()) {
//            final UpcomingEvent upcomingEvent2 = upcomingEventList.get(eventIndex2);
//            holder.eventRelativeLayout2.setVisibility(View.VISIBLE);
//            holder.eventNameTextView2.setText(upcomingEvent2.getEventName());
//            holder.eventDateTextView2.setText(upcomingEvent2.getEventDate());
//            holder.countdownTextView2.setText(upcomingEvent2.getCountdownText());
//
//            holder.eventRelativeLayout2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), EventDetailPage.class);
//                    intent.putExtra("event", upcomingEvent2);
//                    v.getContext().startActivity(intent);
//                }
//            });
//        } else {
//            holder.eventRelativeLayout2.setVisibility(View.GONE);
//        }
//
//        holder.eventRelativeLayout1.setOnClickListener(new View.OnClickListener() {
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
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        RelativeLayout eventRelativeLayout1;
//        TextView eventNameTextView1;
//        TextView eventDateTextView1;
//        TextView countdownTextView1;
//
//        RelativeLayout eventRelativeLayout2;
//        TextView eventNameTextView2;
//        TextView eventDateTextView2;
//        TextView countdownTextView2;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            eventRelativeLayout1 = itemView.findViewById(R.id.eventRelativeLayout1);
//            eventNameTextView1 = itemView.findViewById(R.id.eventNameTextView);
//            eventDateTextView1 = itemView.findViewById(R.id.eventDateTextView);
//            countdownTextView1 = itemView.findViewById(R.id.countdownTextView1);
//
//            eventRelativeLayout2 = itemView.findViewById(R.id.eventRelativeLayout2);
//            //eventLinearLayout2 = itemView.findViewById(R.id.eventLinearLayout2);
//            eventNameTextView2 = itemView.findViewById(R.id.eventNameTextView2);
//            eventDateTextView2 = itemView.findViewById(R.id.eventDateTextView2);
//            countdownTextView2 = itemView.findViewById(R.id.countdownTextView2);
//        }
//    }
//
//    public void startCountdownTimer() {
//        handler.postDelayed(runnable, 0);
//    }
//
//    public void stopCountdownTimer() {
//        handler.removeCallbacks(runnable);
//    }
//
//
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
//
//    private String formatCountdownText(long timeDiff) {
//        // Convert the time difference to days, hours, minutes, and seconds
//        long hours = TimeUnit.MILLISECONDS.toHours(timeDiff);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff) % 60;
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff) % 60;
//
//        // Create a formatted countdown text
//        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
//    }
//
//}
//
//

package com.example.planahead_capstone;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        int eventIndex1 = position * 2;
//        int eventIndex2 = position * 2 + 1;
//
//        UpcomingEvent upcomingEvent1 = upcomingEventList.get(eventIndex1);
//        holder.eventRelativeLayout1.setVisibility(View.VISIBLE);
//        holder.eventNameTextView1.setText(upcomingEvent1.getEventName());
//        holder.eventDateTextView1.setText(upcomingEvent1.getEventDate());
//
//        if (eventIndex2 < upcomingEventList.size()) {
//            final UpcomingEvent upcomingEvent2 = upcomingEventList.get(eventIndex2);
//            holder.eventRelativeLayout2.setVisibility(View.VISIBLE);
//            holder.eventNameTextView2.setText(upcomingEvent2.getEventName());
//            holder.eventDateTextView2.setText(upcomingEvent2.getEventDate());
//
//            holder.eventRelativeLayout2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), EventDetailPage.class);
//                    intent.putExtra("event", upcomingEvent2);
//                    v.getContext().startActivity(intent);
//                }
//            });
//        } else {
//            holder.eventRelativeLayout2.setVisibility(View.GONE);
//        }
//
//        // Update countdown text
//        holder.countdownTextView1.setText(upcomingEvent1.getCountdownText());
//        if (eventIndex2 < upcomingEventList.size()) {
//            UpcomingEvent upcomingEvent2 = upcomingEventList.get(eventIndex2);
//            holder.countdownTextView2.setText(upcomingEvent2.getCountdownText());
//        }
//    }
@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    int eventIndex1 = position * 2;
    int eventIndex2 = position * 2 + 1;

    UpcomingEvent upcomingEvent1 = upcomingEventList.get(eventIndex1);
    holder.eventRelativeLayout1.setVisibility(View.VISIBLE);
    holder.eventNameTextView1.setText(upcomingEvent1.getEventName());
    holder.eventDateTextView1.setText(upcomingEvent1.getEventDate());

    if (eventIndex2 < upcomingEventList.size()) {
        final UpcomingEvent upcomingEvent2 = upcomingEventList.get(eventIndex2);
        holder.eventRelativeLayout2.setVisibility(View.VISIBLE);
        holder.eventNameTextView2.setText(upcomingEvent2.getEventName());
        holder.eventDateTextView2.setText(upcomingEvent2.getEventDate());

        holder.eventRelativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetailPage.class);
                intent.putExtra("event", upcomingEvent2);
                v.getContext().startActivity(intent);
            }
        });

        // Update countdown text for event 2
        holder.countdownTextView2.setText(upcomingEvent2.getCountdownText());
    } else {
        holder.eventRelativeLayout2.setVisibility(View.GONE);
    }

    // Update countdown text for event 1
    holder.countdownTextView1.setText(upcomingEvent1.getCountdownText());
}

    @Override
    public int getItemCount() {
        return (int) Math.ceil(upcomingEventList.size() / 2.0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout eventRelativeLayout1;
        TextView eventNameTextView1;
        TextView eventDateTextView1;
        TextView countdownTextView1;

        RelativeLayout eventRelativeLayout2;
        TextView eventNameTextView2;
        TextView eventDateTextView2;
        TextView countdownTextView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventRelativeLayout1 = itemView.findViewById(R.id.eventRelativeLayout1);
            eventNameTextView1 = itemView.findViewById(R.id.eventNameTextView);
            eventDateTextView1 = itemView.findViewById(R.id.eventDateTextView);
            countdownTextView1 = itemView.findViewById(R.id.countdownTextView1);

            eventRelativeLayout2 = itemView.findViewById(R.id.eventRelativeLayout2);
            eventNameTextView2 = itemView.findViewById(R.id.eventNameTextView2);
            eventDateTextView2 = itemView.findViewById(R.id.eventDateTextView2);
            countdownTextView2 = itemView.findViewById(R.id.countdownTextView2);
        }
    }

    public void startCountdownTimer() {
        handler.postDelayed(runnable, 0);
    }

    public void stopCountdownTimer() {
        handler.removeCallbacks(runnable);
    }


private void updateCountdownTimers() {
    // Get the current time
    long currentTime = System.currentTimeMillis();

    for (UpcomingEvent event : upcomingEventList) {
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
    return String.format(Locale.getDefault(), "%d:%02d:%02d:%02d", days, hours, minutes, seconds);
}


}
