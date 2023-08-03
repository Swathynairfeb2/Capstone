package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ThemeIdeasActivity extends AppCompatActivity {
    private RecyclerView recyclerViewThemeIdeas;
    private Button buttonClose;

    private List<ThemeIdea> themeIdeas;
    private ThemeIdeasAdapter themeIdeasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_ideas);

        recyclerViewThemeIdeas = findViewById(R.id.recyclerViewThemeIdeas);
        buttonClose = findViewById(R.id.buttonClose);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);

        // Initialize theme ideas list and adapter
        themeIdeas = generateThemeIdeas(); // Implement this method to generate your theme ideas list
        themeIdeasAdapter = new ThemeIdeasAdapter(themeIdeas);

        // Set the adapter on the RecyclerView
        recyclerViewThemeIdeas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewThemeIdeas.setAdapter(themeIdeasAdapter);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity
            }
        });
    }

    // Implement this method to generate your theme ideas list
    private List<ThemeIdea> generateThemeIdeas() {
        List<ThemeIdea> themeIdeas = new ArrayList<>();
        // Add your theme ideas here

        // Example theme ideas
        themeIdeas.add(new ThemeIdea("Beach Party", "Bring the beach vibes to your event with sand, sun, and surf!"));
        themeIdeas.add(new ThemeIdea("Masquerade Ball", "An elegant and mysterious theme with masks and formal attire."));
        themeIdeas.add(new ThemeIdea("Retro 80s", "Go back in time with neon colors, leg warmers, and big hair!"));

        return themeIdeas;
    }

    public static class ThemeIdea {
        private String title;
        private String description;

        public ThemeIdea(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

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

            public ThemeIdeaViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.textViewTitle);
                descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            }

            public void bind(ThemeIdea themeIdea) {
                titleTextView.setText(themeIdea.getTitle());
                descriptionTextView.setText(themeIdea.getDescription());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle item click here
                    }
                });
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Fragment selectedFragment = null;
                    Intent intent;

                    switch (item.getItemId()) {
                        case R.id.menu_home:
                            // Handle the home action
                            Toast.makeText(ThemeIdeasActivity.this, "Home", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            intent = new Intent(ThemeIdeasActivity.this, CategoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            intent = new Intent(ThemeIdeasActivity.this, EventsActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_account:
                            // Handle the my account action
                            intent = new Intent(ThemeIdeasActivity.this, UserAccountSettings.class);
                            startActivity(intent);
                            break;
                    }

                    return true;
                }
            };
}
