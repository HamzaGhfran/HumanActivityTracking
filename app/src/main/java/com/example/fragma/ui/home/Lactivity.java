package com.example.fragma.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragma.R;

public class Lactivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lactivity);

        // Retrieve the passed username and password from the intent extras
        Intent intent = getIntent();
        String passedUsername = intent.getStringExtra("username");
        String passedPassword = intent.getStringExtra("password");

        Button btnl = findViewById(R.id.buttonLogin);
        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user data from SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Lactivity.this);
                String savedUsername = preferences.getString("username", "");
                String savedPassword = preferences.getString("password", "");

                // Check if entered username and password match the saved values
                if (passedUsername.equals(savedUsername) && passedPassword.equals(savedPassword)) {
                    // Username and password matched, proceed with login
                    Intent intent = new Intent(Lactivity.this, forfragmentimport.class);
                    startActivity(intent);
                } else {
                    // Invalid credentials, show error message or perform necessary actions
                    // For example, display a Toast message
                    Toast.makeText(Lactivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
