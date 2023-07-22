package com.example.fragma.ui.slideshow;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragma.R;
import com.example.fragma.ui.gallery.GalleryFragment;

public class forFragmentuse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_fragmentuse);
        FrameLayout fragmentContainer =findViewById(R.id.fragmentContainer1);

        // Create an instance of the fragment
        GalleryFragment galleryFragment;
        galleryFragment = new GalleryFragment();

        // Replace the fragment container with the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), galleryFragment)
                .commit();
    }
}