package com.example.fragma.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragma.R;

public class signupactivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText ageEditText;
    private RadioGroup genderRadioGroup;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        ageEditText = findViewById(R.id.ageEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        registerButton = findViewById(R.id.registerButton);

        // Add text change listeners to all fields
        usernameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
        confirmPasswordEditText.addTextChangedListener(textWatcher);
        ageEditText.addTextChangedListener(textWatcher);
        genderRadioGroup.setOnCheckedChangeListener(radioGroupListener);

        // Initially disable the register button
        registerButton.setEnabled(false);
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save user data to SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(signupactivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", usernameEditText.getText().toString());
                editor.putString("password", passwordEditText.getText().toString());
                editor.apply();

                // Start the Lactivity and pass the username and password as extras
                Intent intent = new Intent(signupactivity.this, Lactivity.class);
                intent.putExtra("username", usernameEditText.getText().toString());
                intent.putExtra("password", passwordEditText.getText().toString());
                startActivity(intent);
            }
        });

    }

    // TextWatcher to monitor text changes in all fields
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            // Check if all fields are filled
            boolean allFieldsFilled = !usernameEditText.getText().toString().isEmpty()
                    && !passwordEditText.getText().toString().isEmpty()
                    && !confirmPasswordEditText.getText().toString().isEmpty()
                    && !ageEditText.getText().toString().isEmpty();

            // Enable/disable the register button based on field validation
            registerButton.setEnabled(allFieldsFilled);
        }
    };

    // RadioGroup.OnCheckedChangeListener to monitor gender radio button changes
    private final RadioGroup.OnCheckedChangeListener radioGroupListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // Check if all fields are filled
                    boolean allFieldsFilled = !usernameEditText.getText().toString().isEmpty()
                            && !passwordEditText.getText().toString().isEmpty()
                            && !confirmPasswordEditText.getText().toString().isEmpty()
                            && !ageEditText.getText().toString().isEmpty();

                    // Enable/disable the register button based on field validation
                    registerButton.setEnabled(allFieldsFilled);
                    if (allFieldsFilled) {
                        registerButton.setTextColor(getResources().getColor(R.color.white));
                        registerButton.setText("Register");
                    } else {
                        registerButton.setTextColor(getResources().getColor(R.color.red));
                        registerButton.setText("First fill all the fields.");
                    }
                }
            };

}
