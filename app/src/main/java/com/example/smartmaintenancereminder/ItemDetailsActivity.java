package com.example.smartmaintenancereminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

public class ItemDetailsActivity extends AppCompatActivity {

    TextView txtDetails;
    DatabaseReference ref;

    // lulu
    Button btnEdit, btnDelete;
    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // lulu
        txtStatus = findViewById(R.id.txtStatus);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        txtDetails = findViewById(R.id.txtDetails);

        String itemId = getIntent().getStringExtra("itemId");
        String path = getIntent().getStringExtra("path");

        ref = FirebaseDatabase.getInstance().getReference(path);

        ref.child(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("carName").getValue(String.class);
                String plate = snapshot.child("plateNumber").getValue(String.class);
                String type = snapshot.child("type").getValue(String.class);
                String maintenance = snapshot.child("maintenanceType").getValue(String.class);
                String last = snapshot.child("lastMaintenance").getValue(String.class);
                String next = snapshot.child("nextMaintenance").getValue(String.class);
                String notes = snapshot.child("notes").getValue(String.class);

                String details =
                        "Car Name: " + name +
                                "\nPlate: " + plate +
                                "\nUser Type: " + type +
                                "\nMaintenance: " + maintenance +
                                "\nLast Maintenance: " + last +
                                "\nNext Maintenance: " + next +
                                "\nNotes: " + notes;

                txtDetails.setText(details);

                // lulu
                btnDelete.setOnClickListener(v -> {
                    ref.child(itemId).removeValue().addOnSuccessListener(unused -> {
                        Toast.makeText(ItemDetailsActivity.this, "Deleted!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    });
                });

                // lulu
                btnEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(ItemDetailsActivity.this, EditItemActivity.class);
                    intent.putExtra("itemId", itemId);
                    intent.putExtra("path", path);
                    intent.putExtra("carName", name);
                    intent.putExtra("plate", plate);
                    intent.putExtra("last", last);
                    intent.putExtra("next", next);
                    intent.putExtra("notes", notes);
                    startActivity(intent);
                });

                // lulu
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("d/M/yyyy");
                    java.util.Date nextD = sdf.parse(next);
                    java.util.Date today = new java.util.Date();

                    if (today.after(nextD)) {
                        txtStatus.setText("Status: Maintenance is OVERDUE");
                        txtStatus.setTextColor(android.graphics.Color.RED);
                    } else {
                        txtStatus.setText("Status: Maintenance is UPCOMING");
                        txtStatus.setTextColor(android.graphics.Color.parseColor("#FF9800"));
                    }
                } catch (Exception e) {
                    txtStatus.setText("Status: Pending");
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}