package com.example.fragma.ui.slideshow;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragma.R;

public class timeractivity extends AppCompatActivity {

    private int number = 0;
    private int required;
    private EditText editText1;
    private TextView textView;
    private TextView textView3;
    private TextView textView33;
    private TextView textView4;

    private Handler handler;
    private Runnable runnable;
    private boolean isTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeractivity);
        textView = findViewById(R.id.tx2);
        textView3 = findViewById(R.id.tx3);
        handler = new Handler();
        isTimerRunning = false;
        Button button2 = findViewById(R.id.btn2);
        button2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        Button button1 = findViewById(R.id.btn1);
        button1.setBackgroundTintList(ColorStateList.valueOf(Color.DKGRAY));
        editText1 = findViewById(R.id.E_t1);
        editText1.setBackgroundTintList(ColorStateList.valueOf(Color.DKGRAY));


        textView33=findViewById(R.id.tv3);
        textView4=findViewById(R.id.tv4);

    }

    public void touchme(View view) {
        if (!isTimerRunning) {
            startTimer();
        } else {
            stopTimer();
        }
        String input = editText1.getText().toString();
        if (!input.isEmpty()) {
            required = Integer.parseInt(input);
        }


    }

    public void reset(View view) {
        stopTimer();
        resetTimer();
    }

    private void startTimer() {
        isTimerRunning = true;

        runnable = new Runnable() {
            @Override
            public void run() {
                textView.setText(String.valueOf(number+1));
                number++;
                handler.postDelayed(this, 1000); // Delay of 1 second (1000 milliseconds)

                if (required > 0 && number == required) {
                    isTimerRunning = false;

                    handler.removeCallbacks(runnable);
                    if (number<60)
                    {
                        textView3.setText("Your time is " + (number ) + " Second ");
                    }
                    else {
                        int minutes = number / 60;
                        int seconds = number % 60;
                        textView3.setText("Your time is " + minutes + " minute " + (seconds) + " second");
                    }
                    textView33.setText("Congratulation!");
                    textView4.setText("You completed the settled target.");

                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    private void stopTimer() {
        isTimerRunning = false;

        handler.removeCallbacks(runnable);
        if (number<60)
        {
            textView3.setText("Your time is " + (number - 1) + " Second ");
        }
        else {
            int minutes = number / 60;
            int seconds = number % 60;
            textView3.setText("Your time is " + minutes + " minute " + (seconds-1) + " second");
        }
    }

    private void resetTimer() {
        number = 0;
        textView.setText("0");
        textView3.setText("Your time is " + number + " Second ");
    }
}