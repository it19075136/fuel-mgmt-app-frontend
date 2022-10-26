package com.example.fuel_mgmt_app_frontend.queue;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fuel_mgmt_app_frontend.R;
import com.google.android.material.textview.MaterialTextView;

public class CustomStationCard extends AppCompatActivity {

    MaterialTextView stationName,location;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_station_card);

        stationName = findViewById(R.id.stationName);
        location = findViewById(R.id.location);

        intent = getIntent();

        stationName.setText(intent.getStringExtra("stationName"));
        location.setText(intent.getStringExtra("location"));

    }
}
