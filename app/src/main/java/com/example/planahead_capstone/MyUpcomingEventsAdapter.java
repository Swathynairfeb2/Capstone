package com.example.planahead_capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpcomingEvent upcomingEvent = upcomingEventList.get(position);

        // Bind data to the views in the item layout
        holder.eventImageView.setImageResource(upcomingEvent.getImageResource());
        holder.eventNameTextView.setText(upcomingEvent.getEventName());
        holder.eventDateTextView.setText(upcomingEvent.getEventDate());
    }

    @Override
    public int getItemCount() {
        return upcomingEventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView eventImageView;
        TextView eventNameTextView;
        TextView eventDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.eventImageView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                UpcomingEvent upcomingEvent = upcomingEventList.get(position);
                // Handle the click event for the item
                // Perform the desired action, such as opening a detailed view of the event
                // You can start a new activity or fragment, or show a dialog, etc.
            }
        }
    }
}
