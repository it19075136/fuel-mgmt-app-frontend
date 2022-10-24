package com.example.fuel_mgmt_app_frontend.FuelStation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.fuel_mgmt_app_frontend.R;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

public class FuelStationBasicDetails extends AppCompatActivity {

    MaterialButton availabilityBtn, availSubmitButton, proceedBtn;
    ToggleButton existPetrolTgl, existDieselTgl, existSuperDieselTgl, existSuperPetrolTgl;
    HashMap<String,Boolean> availabilities;
    boolean availabilityCheck = false;
    Intent intent;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_basic_details);

        availabilityBtn = findViewById(R.id.availability);
        proceedBtn = findViewById(R.id.next);

        availabilities = new HashMap<>();

        createDialog();

        availabilityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(availabilityCheck){
//                    create fuel station in mongo db
                    Toast.makeText(FuelStationBasicDetails.this, "Fuel station created successfully", Toast.LENGTH_SHORT).show();
                    intent = new Intent(FuelStationBasicDetails.this,FuelStationList.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(FuelStationBasicDetails.this, "Please set availabilities in order to proceed", Toast.LENGTH_SHORT).show();

            }
        });

    }

//    prepare custom dialog for availabilities
    private void createDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        existDieselTgl = view.findViewById(R.id.diesel_switch);
        existPetrolTgl = view.findViewById(R.id.petrol_switch);
        existSuperDieselTgl = view.findViewById(R.id.super_diesel_switch);
        existSuperPetrolTgl = view.findViewById(R.id.super_petrol_switch);
        availSubmitButton = view.findViewById(R.id.confirm);

        availSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                availabilities.put("petrol", existPetrolTgl.isChecked());
                availabilities.put("super petrol", existSuperPetrolTgl.isChecked());
                availabilities.put("diesel", existDieselTgl.isChecked());
                availabilities.put("super diesel", existSuperDieselTgl.isChecked());
                dialog.dismiss();
                availabilityCheck = true;
                Toast.makeText(FuelStationBasicDetails.this,"Availabilities set successfully",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view)
                .setTitle("Set Availabilities");

        dialog = builder.create();

    }

}