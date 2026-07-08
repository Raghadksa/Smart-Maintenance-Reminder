package com.example.smartmaintenancereminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseUserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);

        // Initialize buttons from the XML layout
        Button btnIndividuals = findViewById(R.id.btnIndividuals);
        Button btnCompanies = findViewById(R.id.btnCompanies);

        // Set listener for Individuals button
        btnIndividuals.setOnClickListener(v -> {
            // Navigate to AddItemActivity and pass user type
            Intent intent = new Intent(ChooseUserTypeActivity.this, AddItemIndividualActivity.class);
            intent.putExtra("USER_TYPE", "Individual");
            startActivity(intent);
        });

        // Set listener for Companies button
        btnCompanies.setOnClickListener(v -> {
            // Navigate to AddItemActivity and pass user type
            Intent intent = new Intent(ChooseUserTypeActivity.this, AddItemCompanyActivity.class);
            intent.putExtra("USER_TYPE", "Company");
            startActivity(intent);
        });
    }
}