package com.example.planahead_capstone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import com.example.planahead_capstone.R;

public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1_layout, container, false);

        ImageView imageView = rootView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.smart_view1); // Replace "your_image" with the actual image name in the drawable folder

        return rootView;
    }
}
