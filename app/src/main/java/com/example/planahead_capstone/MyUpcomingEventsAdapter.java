package com.example.planahead_capstone;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyUpcomingEventsAdapter extends RecyclerView.Adapter<MyUpcomingEventsAdapter.ViewHolder> {

    private List<UpcomingEvent> upcomingEventList;

    public MyUpcomingEventsAdapter(List<UpcomingEvent> upcomingEventList) {
        this.upcomingEventList = upcomingEventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpcomingEvent upcomingEvent = upcomingEventList.get(position * 2);

        // Bind data to the first event views
        holder.eventNameTextView1.setText(upcomingEvent.getEventName());
        holder.eventDateTextView1.setText(upcomingEvent.getEventDate());

        // Check if there is a second event
        if (position * 2 + 1 < upcomingEventList.size()) {
            UpcomingEvent secondUpcomingEvent = upcomingEventList.get(position * 2 + 1);

            // Bind data to the second event views
            holder.eventNameTextView2.setText(secondUpcomingEvent.getEventName());
            holder.eventDateTextView2.setText(secondUpcomingEvent.getEventDate());

            // Show the second event views
            holder.eventLinearLayout2.setVisibility(View.VISIBLE);
        } else {
            // Hide the second event views if there is no second event
            holder.eventLinearLayout2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        // Divide the number of events by 2 to get the number of rows
        return (int) Math.ceil(upcomingEventList.size() / 2.0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout eventLinearLayout1;
        TextView eventNameTextView1;
        TextView eventDateTextView1;

        LinearLayout eventLinearLayout2;
        TextView eventNameTextView2;
        TextView eventDateTextView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventLinearLayout1 = itemView.findViewById(R.id.eventLinearLayout1);
            eventNameTextView1 = itemView.findViewById(R.id.eventNameTextView);
            eventDateTextView1 = itemView.findViewById(R.id.eventDateTextView);

            eventLinearLayout2 = itemView.findViewById(R.id.eventLinearLayout2);
            eventNameTextView2 = itemView.findViewById(R.id.eventNameTextView2);
            eventDateTextView2 = itemView.findViewById(R.id.eventDateTextView2);

            eventLinearLayout1.setOnClickListener(this);
            eventLinearLayout2.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Get the clicked event
                UpcomingEvent clickedEvent = upcomingEventList.get(position * 2);

                // Start the EventDetailPage activity here, passing the event details
                Intent intent = new Intent(v.getContext(), EventDetailPage.class);
                intent.putExtra("eventName", clickedEvent.getEventName());
                intent.putExtra("eventDate", clickedEvent.getEventDate());
                // Add any additional event details to the intent
                // ...

                v.getContext().startActivity(intent);
            }
        }
    }
}
