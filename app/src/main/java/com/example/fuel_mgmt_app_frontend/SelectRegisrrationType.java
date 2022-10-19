package com.example.fuel_mgmt_app_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRegisrrationType extends AppCompatActivity {

    Button userTypebtn,fuelStationTypebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_regisrration_type);

        userTypebtn = findViewById(R.id.UserType);
        fuelStationTypebtn = findViewById(R.id.FuelStationType);

        userTypebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(SelectRegisrrationType.this,UserRegistration.class);
                startActivity(intent);
            }
        });

        fuelStationTypebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(SelectRegisrrationType.this,FuelStationRegistration.class);
                startActivity(intent);
            }
        });
    }
}