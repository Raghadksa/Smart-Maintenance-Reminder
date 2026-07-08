package com.example.smartmaintenancereminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Timer to move to the next screen after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Moving from Splash screen to Choose User Type screen
                Intent intent = new Intent(SplashActivity.this, ChooseUserTypeActivity.class);
                startActivity(intent);
                finish(); // Close Splash screen so the user cannot go back to it
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}