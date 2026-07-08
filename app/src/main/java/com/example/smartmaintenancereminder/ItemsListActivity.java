package com.example.smartmaintenancereminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class ItemsListActivity extends AppCompatActivity {

    ListView listViewCars;
    ArrayList<String> carList, idList, pathList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(ItemsListActivity.this, ChooseUserTypeActivity.class));
                finish();
            }
        });

        listViewCars = findViewById(R.id.listViewCars);

        carList = new ArrayList<>();
        idList = new ArrayList<>();
        pathList = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                carList
        );

        listViewCars.setAdapter(adapter);

        listViewCars.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent = new Intent(ItemsListActivity.this, ItemDetailsActivity.class);
            intent.putExtra("itemId", idList.get(position));
            intent.putExtra("path", pathList.get(position));
            startActivity(intent);
        });

        loadData("individuals");
        loadData("companies");
    }

    private void loadData(String path) {

        FirebaseDatabase.getInstance().getReference(path)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            String name = dataSnapshot.child("carName").getValue(String.class);
                            String itemId = dataSnapshot.child("id").getValue(String.class);
                            String type = dataSnapshot.child("type").getValue(String.class);

                            carList.add(name + " - " + type);
                            idList.add(itemId);
                            pathList.add(path);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }
}