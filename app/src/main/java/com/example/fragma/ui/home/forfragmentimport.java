package com.example.fragma.ui.home;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragma.R;
import com.example.fragma.ui.slideshow.SlideshowFragment;

public class forfragmentimport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forfragmentimport);

        // Find the fragment container
        FrameLayout fragmentContainer = findViewById(R.id.fragmentContainer);

        // Create an instance of the fragment
        SlideshowFragment slideshowFragment = new SlideshowFragment();

        // Replace the fragment container with the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), slideshowFragment)
                .commit();
    }
}