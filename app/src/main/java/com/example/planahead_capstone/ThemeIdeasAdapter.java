package com.example.planahead_capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ThemeIdeasAdapter extends RecyclerView.Adapter<ThemeIdeasAdapter.ThemeIdeaViewHolder> {
    private List<ThemeIdea> themeIdeas;

    public ThemeIdeasAdapter(List<ThemeIdea> themeIdeas) {
        this.themeIdeas = themeIdeas;
    }

    @NonNull
    @Override
    public ThemeIdeaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_idea, parent, false);
        return new ThemeIdeaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeIdeaViewHolder holder, int position) {
        ThemeIdea themeIdea = themeIdeas.get(position);
        holder.bind(themeIdea);
    }

    @Override
    public int getItemCount() {
        return themeIdeas.size();
    }

    public class ThemeIdeaViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView imageView; // Add ImageView to display the image

        public ThemeIdeaViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            imageView = itemView.findViewById(R.id.imageView); // Initialize ImageView
        }

        public void bind(ThemeIdea themeIdea) {
            titleTextView.setText(themeIdea.getTitle());
            descriptionTextView.setText(themeIdea.getDescription());
            imageView.setImageResource(themeIdea.getImageResource()); // Set the image resource

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle item click here
                }
            });
        }
    }
}
