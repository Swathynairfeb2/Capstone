//package com.example.planahead_capstone;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//import androidx.viewpager.widget.ViewPager;
//
//
//import com.example.planahead_capstone.Fragment1;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//
//public class HomeActivity extends AppCompatActivity {
//    private Button menu_my_events;
//    private ViewPager viewPager;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        // Initialize ViewPager
//        viewPager = findViewById(R.id.viewPager);
//        setupViewPager(viewPager);
//
//        // Bottom Navigation View
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);
//
//
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        // Create and set up your adapter for ViewPager
//        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new Fragment1(), "Fragment 1");
//        adapter.addFragment(new Fragment2(), "Fragment 2");
//        adapter.addFragment(new Fragment3(), "Fragment 3");
//        viewPager.setAdapter(adapter);
//    }
//    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(MenuItem item) {
//                    Fragment selectedFragment = null;
//                    Intent intent;
//
//                    switch (item.getItemId()) {
//                        case R.id.menu_home:
//                            // Handle the home action
//                            Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                            break;
//                        case R.id.menu_categories:
//                            // Handle the categories action
//                            intent = new Intent(HomeActivity.this, CategoryActivity.class);
//                            startActivity(intent);
//                            break;
//                        case R.id.menu_my_events:
//                            // Start the EventCreationActivity
//                            intent = new Intent(HomeActivity.this, EventsActivity.class);
//                            startActivity(intent);
//                            break;
//                        case R.id.menu_my_account:
//                            // Handle the my account action
//                            Toast.makeText(HomeActivity.this, "My Account", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//
//                    return true;
//                }
//            };
//}
package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize ViewPager
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);
    }

    private void setupViewPager(ViewPager viewPager) {
        // Create and set up your adapter for ViewPager
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "Fragment 1");
        adapter.addFragment(new Fragment2(), "Fragment 2");
        adapter.addFragment(new Fragment3(), "Fragment 3");
        viewPager.setAdapter(adapter);
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
                            Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            intent = new Intent(HomeActivity.this, CategoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            intent = new Intent(HomeActivity.this, EventsActivity.class);
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
