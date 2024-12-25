package com.example.focusedtracking;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PomodoroActivity extends AppCompatActivity {

    private TextView timerText;
    private Button startStopButton;
    private boolean isRunning = false;
    private int secondsLeft = 25 * 60; // 25 minutes
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        timerText = findViewById(R.id.timerText);
        startStopButton = findViewById(R.id.startStopButton);

        startStopButton.setOnClickListener(view -> toggleTimer());
        updateTimerDisplay();
    }

    private void toggleTimer() {
        if (isRunning) {
            handler.removeCallbacksAndMessages(null);
            startStopButton.setText("Start");
        } else {
            handler.post(timerRunnable);
            startStopButton.setText("Stop");
        }
        isRunning = !isRunning;
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (secondsLeft > 0) {
                secondsLeft--;
                updateTimerDisplay();
                handler.postDelayed(this, 1000);
            } else {
                startStopButton.setText("Start");
                isRunning = false;
            }
        }
    };

    private void updateTimerDisplay() {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        timerText.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
