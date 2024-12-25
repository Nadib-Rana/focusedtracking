package com.example.focusedtracking;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MotivationalMessagesActivity extends AppCompatActivity {

    private String[] quotes = {
            "The secret of getting ahead is getting started.",
            "Believe you can and you're halfway there.",
            "Don't watch the clock; do what it does. Keep going.",
            "Start where you are. Use what you have. Do what you can.",
            "Success is not final, failure is not fatal: It is the courage to continue that counts."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivational_messages);

        TextView quoteText = findViewById(R.id.quoteText);
        Random random = new Random();
        String randomQuote = quotes[random.nextInt(quotes.length)];
        quoteText.setText(randomQuote);
    }
}
