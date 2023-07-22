package com.example.fragma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class composeemail extends AppCompatActivity {
    private EditText editTextBody;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composeemail);

        editTextBody = findViewById(R.id.editTextBody);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient ="uk6164874@gmail.com";
                String body = editTextBody.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);

                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });
    }
}