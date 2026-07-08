package com.example.smartmaintenancereminder;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditItemActivity extends AppCompatActivity {

    // lulu
    EditText editName, editPlate, editLast, editNext, editNotes;
    Button btnSave;
    DatabaseReference ref;
    String itemId, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // lulu
        editName = findViewById(R.id.editCarName);
        editPlate = findViewById(R.id.editPlateNumber);
        editLast = findViewById(R.id.editLastMaint);
        editNext = findViewById(R.id.editNextMaint);
        editNotes = findViewById(R.id.editNotes);
        btnSave = findViewById(R.id.btnUpdateSave);

        itemId = getIntent().getStringExtra("itemId");
        path = getIntent().getStringExtra("path");

        // lulu
        editName.setText(getIntent().getStringExtra("carName"));
        editPlate.setText(getIntent().getStringExtra("plate"));
        editLast.setText(getIntent().getStringExtra("last"));
        editNext.setText(getIntent().getStringExtra("next"));
        editNotes.setText(getIntent().getStringExtra("notes"));

        ref = FirebaseDatabase.getInstance().getReference(path).child(itemId);

        setupDatePicker(editLast);
        setupDatePicker(editNext);

        btnSave.setOnClickListener(v -> {
            Map<String, Object> updates = new HashMap<>();
            updates.put("carName", editName.getText().toString());
            updates.put("plateNumber", editPlate.getText().toString());
            updates.put("lastMaintenance", editLast.getText().toString());
            updates.put("nextMaintenance", editNext.getText().toString());
            updates.put("notes", editNotes.getText().toString());

            ref.updateChildren(updates).addOnSuccessListener(unused -> {
                Toast.makeText(this, "Changes Saved!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    // lulu
    private void setupDatePicker(EditText editText) {
        editText.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) ->
                    editText.setText(day + "/" + (month + 1) + "/" + year),
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });
    }
}