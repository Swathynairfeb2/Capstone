package com.example.planahead_capstone;

import java.util.concurrent.TimeUnit;

public class CountdownUtils {
    public static String getCountdownText(long countdownTime) {
        // Calculate days, hours, minutes, and seconds from the countdown time
        long days = TimeUnit.MILLISECONDS.toDays(countdownTime);
        long hours = TimeUnit.MILLISECONDS.toHours(countdownTime) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(countdownTime) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(countdownTime) % 60;

        // Construct the countdown text
        StringBuilder countdownTextBuilder = new StringBuilder();

        if (days > 0) {
            countdownTextBuilder.append(days);
            countdownTextBuilder.append(" days ");
        }

        countdownTextBuilder.append(String.format("%02d", hours));
        countdownTextBuilder.append(":");
        countdownTextBuilder.append(String.format("%02d", minutes));
        countdownTextBuilder.append(":");
        countdownTextBuilder.append(String.format("%02d", seconds));

        return countdownTextBuilder.toString();
    }
}

