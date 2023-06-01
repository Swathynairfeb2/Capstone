package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
private Button menu_my_events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);




    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.menu_home:
                            // Handle the home action
                            Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            Toast.makeText(HomeActivity.this, "Categories", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            Intent intent = new Intent(HomeActivity.this, EventCreationActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.menu_my_account:
                            // Handle the my account action
                            Toast.makeText(HomeActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    return true;
                }
            };
}
