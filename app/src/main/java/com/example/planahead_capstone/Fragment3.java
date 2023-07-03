package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {

    private ImageView imageView;
    private int[] imageResources = {R.drawable.smart_view3, R.drawable.smart_view1, R.drawable.smart_view2};
    private int currentPosition = 0;
    private Handler handler;
    private Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment3_layout, container, false);

        imageView = rootView.findViewById(R.id.imageView);
        imageView.setImageResource(imageResources[currentPosition]);

        // Set click listener for the image
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                navigateToIdeaPage();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        startImageSlider();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopImageSlider();
    }

    private void startImageSlider() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentPosition = (currentPosition + 1) % imageResources.length;
                imageView.setImageResource(imageResources[currentPosition]);
                handler.postDelayed(this, 3000); // Change the duration as per your requirement (e.g., 3000ms = 3 seconds)
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void stopImageSlider() {
        handler.removeCallbacks(runnable);
    }

    private void navigateToIdeaPage() {
        // Start the IdeaActivity or perform any navigation logic
        Intent intent = new Intent(getActivity(), ThemeIdeasActivity.class);
        startActivity(intent);
    }
}
