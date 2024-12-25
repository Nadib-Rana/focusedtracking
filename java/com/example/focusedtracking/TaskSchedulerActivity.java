package com.example.focusedtracking;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskSchedulerActivity extends AppCompatActivity {

    private LinearLayout taskList;
    private EditText inputTask;
    private Button addTaskButton;
    private String selectedTime = "No Time Set";
    private ArrayList<Task> tasks = new ArrayList<>(); // To keep track of tasks
    private Handler timeHandler = new Handler();
    private Runnable timeCheckerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_scheduler);

        taskList = findViewById(R.id.taskList);
        inputTask = findViewById(R.id.inputTask);
        Button pickTimeButton = findViewById(R.id.pickTimeButton);
        addTaskButton = findViewById(R.id.addTaskButton);

        pickTimeButton.setOnClickListener(view -> openTimePicker());
        addTaskButton.setOnClickListener(view -> addTask());

        startTimeChecker(); // Start checking task times
    }

    private void openTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> selectedTime = String.format("%02d:%02d", hourOfDay, minute),
                0, 0, true);
        timePickerDialog.show();
    }

    private void addTask() {
        String taskName = inputTask.getText().toString().trim();
        if (!taskName.isEmpty() && !selectedTime.equals("No Time Set")) {
            Task task = new Task(taskName, selectedTime);
            tasks.add(task);

            addTaskView(task);
            inputTask.setText("");
            selectedTime = "No Time Set";
        } else {
            Toast.makeText(this, "Please enter a task and set a time.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addTaskView(Task task) {
        View taskView = getLayoutInflater().inflate(R.layout.task_item, null);
        TextView taskNameView = taskView.findViewById(R.id.taskName);
        TextView taskTimeView = taskView.findViewById(R.id.taskTime);
        ImageButton editButton = taskView.findViewById(R.id.editTaskButton);
        ImageButton deleteButton = taskView.findViewById(R.id.deleteTaskButton);
        Button completeButton = taskView.findViewById(R.id.completeTaskButton);

        taskNameView.setText(task.getName());
        taskTimeView.setText(task.getTime());

        editButton.setOnClickListener(view -> editTask(task, taskNameView, taskTimeView));
        deleteButton.setOnClickListener(view -> {
            taskList.removeView(taskView);
            tasks.remove(task);
        });

        completeButton.setOnClickListener(view -> {
            task.setCompleted(true);
            taskView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            completeButton.setVisibility(View.GONE);  // Hide the complete button once clicked
        });

        taskList.addView(taskView);
    }

    private void editTask(Task task, TextView taskNameView, TextView taskTimeView) {
        inputTask.setText(task.getName());
        selectedTime = task.getTime();
        addTaskButton.setText("Update Task");

        addTaskButton.setOnClickListener(view -> {
            task.setName(inputTask.getText().toString().trim());
            task.setTime(selectedTime);
            taskNameView.setText(task.getName());
            taskTimeView.setText(task.getTime());

            inputTask.setText("");
            selectedTime = "No Time Set";
            addTaskButton.setText("Add Task");
            addTaskButton.setOnClickListener(view1 -> addTask()); // Reset the button listener
        });
    }

    private void startTimeChecker() {
        timeCheckerRunnable = new Runnable() {
            @Override
            public void run() {
                checkTaskTimes();
                timeHandler.postDelayed(this, 60000); // Check every minute
            }
        };
        timeHandler.post(timeCheckerRunnable);
    }

    private void checkTaskTimes() {
        Calendar currentTime = Calendar.getInstance();
        String currentTimeStr = String.format("%02d:%02d", currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE));

        for (Task task : tasks) {
            if (task.getTime().equals(currentTimeStr) && !task.isCompleted()) {
                // Change color to yellow when task time arrives
                changeTaskColorToYellow(task);
            }
        }
    }

    private void changeTaskColorToYellow(Task task) {
        // Find the task in the UI and change its color (assume the task view is found)
        for (int i = 0; i < taskList.getChildCount(); i++) {
            View taskView = taskList.getChildAt(i);
            TextView taskNameView = taskView.findViewById(R.id.taskName);
            TextView taskTimeView = taskView.findViewById(R.id.taskTime);

            if (taskNameView.getText().toString().equals(task.getName()) &&
                    taskTimeView.getText().toString().equals(task.getTime())) {
                taskView.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
            }
        }
    }

    private static class Task {
        private String name;
        private String time;
        private boolean isCompleted;

        public Task(String name, String time) {
            this.name = name;
            this.time = time;
            this.isCompleted = false;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

        public void setCompleted(boolean completed) {
            isCompleted = completed;
        }
    }
}
