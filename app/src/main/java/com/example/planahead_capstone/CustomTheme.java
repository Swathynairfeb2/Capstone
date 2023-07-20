package com.example.planahead_capstone;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class CustomTheme extends Application {

    private static final String PREF_DARK_MODE = "dark_mode_pref";
    private boolean isDarkModeEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        loadDarkMode();
        updateTheme();
    }

    public void setDarkModeEnabled(boolean isEnabled) {
        isDarkModeEnabled = isEnabled;
        saveDarkMode();
        updateTheme();
    }

    private void updateTheme() {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveDarkMode() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_DARK_MODE, isDarkModeEnabled);
        editor.apply();
    }

    private void loadDarkMode() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        isDarkModeEnabled = sharedPreferences.getBoolean(PREF_DARK_MODE, false);
    }
}
