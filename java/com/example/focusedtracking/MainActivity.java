package com.example.focusedtracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Navigate to Task Scheduler
    public void openTaskScheduler(View view) {
        startActivity(new Intent(this, TaskSchedulerActivity.class));
    }

    // Navigate to Pomodoro Timer
    public void openPomodoro(View view) {
        startActivity(new Intent(this, PomodoroActivity.class));
    }

    // Navigate to Progress Tracker
    public void openProgressTracker(View view) {
        startActivity(new Intent(this, ProgressTrackerActivity.class));
    }

    // Navigate to Goal Setting
    public void openGoalSetting(View view) {
        startActivity(new Intent(this, GoalSettingActivity.class));
    }

    // Navigate to Motivational Messages
    public void openMotivationalMessages(View view) {
        startActivity(new Intent(this, MotivationalMessagesActivity.class));
    }
}
