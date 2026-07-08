package com.example.smartmaintenancereminder;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItemIndividualActivity extends AppCompatActivity {

    EditText carName, plateNumber, kilometers, lastMaintenance,
            nextMaintenance, driverName, cost, notes;

    Spinner fuelType, maintenanceType;
    Switch notificationSwitch;
    Button btnSave;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_individual);

        carName = findViewById(R.id.carName);
        plateNumber = findViewById(R.id.plateNumber);
        fuelType = findViewById(R.id.fuelType);
        kilometers = findViewById(R.id.kilometers);
        maintenanceType = findViewById(R.id.maintenanceType);
        lastMaintenance = findViewById(R.id.lastMaintenance);
        nextMaintenance = findViewById(R.id.nextMaintenance);
        driverName = findViewById(R.id.driverName);
        cost = findViewById(R.id.cost);
        notificationSwitch = findViewById(R.id.notificationSwitch);
        notes = findViewById(R.id.notes);
        btnSave = findViewById(R.id.btnSave);


        ref = FirebaseDatabase.getInstance().getReference("individuals");

        // Fuel Type
        List<String> fuelList = Arrays.asList(
                "Petrol",
                "Diesel",
                "Hybrid",
                "Electric"
        );

        ArrayAdapter<String> fuelAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                fuelList
        );
        fuelType.setAdapter(fuelAdapter);

        // Maintenance Type
        List<String> maintenanceList = Arrays.asList(
                "Oil Change",
                "Brake Check",
                "Battery Change",
                "Tire Rotation",
                "Wheel Alignment",
                "Engine Check",
                "Air Filter Replacement",
                "Spark Plug Replacement",
                "Cooling System Check",
                "Transmission Service",
                "Other"
        );

        ArrayAdapter<String> maintenanceAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                maintenanceList
        );
        maintenanceType.setAdapter(maintenanceAdapter);

        // Date Picker
        lastMaintenance.setOnClickListener(v -> showDatePicker(lastMaintenance));
        nextMaintenance.setOnClickListener(v -> showDatePicker(nextMaintenance));

        // Save
        btnSave.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        String id = ref.push().getKey();

        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("carName", carName.getText().toString());
        data.put("plateNumber", plateNumber.getText().toString());
        data.put("fuelType", fuelType.getSelectedItem().toString());
        data.put("kilometers", kilometers.getText().toString());
        data.put("maintenanceType", maintenanceType.getSelectedItem().toString());
        data.put("lastMaintenance", lastMaintenance.getText().toString());
        data.put("nextMaintenance", nextMaintenance.getText().toString());
        data.put("driverName", driverName.getText().toString());
        data.put("cost", cost.getText().toString());
        data.put("notifications", notificationSwitch.isChecked());
        data.put("notes", notes.getText().toString());
        data.put("type", "Individual");

        ref.child(id).setValue(data)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddItemIndividualActivity.this, ItemsListActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show());
    }

    private void showDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) ->
                        editText.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }
}
