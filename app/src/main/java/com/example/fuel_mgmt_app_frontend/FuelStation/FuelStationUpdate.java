package com.example.fuel_mgmt_app_frontend.FuelStation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.fuel_mgmt_app_frontend.DBHelper;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.User.MainActivity;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

public class FuelStationUpdate extends AppCompatActivity {

    MaterialButton availabilityBtn, availSubmitButton, proceedBtn;
    ToggleButton existPetrolTgl, existDieselTgl, existSuperDieselTgl, existSuperPetrolTgl;
    HashMap<String,Boolean> availabilities;
    boolean availabilityCheck = false;
    Intent intent;
    ImageView backIcon,signOutIcon;
    DBHelper DB;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_update);

//        availabilityBtn = findViewById(R.id.availability);
//        proceedBtn = findViewById(R.id.next);
//        backIcon =  findViewById(R.id.left_icon);
//        signOutIcon = findViewById(R.id.right_icon);
//        DB = new DBHelper(this);
//        availabilities = new HashMap<>();
//
//        createDialog();
//
//        signOutIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(DB.logedOut()){
//                    intent = new Intent(FuelStationUpdate.this, MainActivity.class);
//                    Toast.makeText(FuelStationUpdate.this, "Log out succesfuly", Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
//                }
//                else{
//                    Toast.makeText(FuelStationUpdate.this, "Log out Unsuccesfuly", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        availabilityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.show();
//            }
//        });
//
//        proceedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(availabilityCheck){
////                    create fuel station in mongo db
//                    Toast.makeText(FuelStationUpdate.this, "Fuel station created successfully", Toast.LENGTH_SHORT).show();
//                    intent = new Intent(FuelStationUpdate.this,FuelStationList.class);
//                    startActivity(intent);
//                }
//                else
//                    Toast.makeText(FuelStationUpdate.this, "Please set availabilities in order to proceed", Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

//    private void createDialog() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
//        existDieselTgl = view.findViewById(R.id.diesel_switch);
//        existPetrolTgl = view.findViewById(R.id.petrol_switch);
//        existSuperDieselTgl = view.findViewById(R.id.super_diesel_switch);
//        existSuperPetrolTgl = view.findViewById(R.id.super_petrol_switch);
//        availSubmitButton = view.findViewById(R.id.confirm);
//
//        availSubmitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                availabilities.put("petrol", existPetrolTgl.isChecked());
//                availabilities.put("super petrol", existSuperPetrolTgl.isChecked());
//                availabilities.put("diesel", existDieselTgl.isChecked());
//                availabilities.put("super diesel", existSuperDieselTgl.isChecked());
//                dialog.dismiss();
//                availabilityCheck = true;
//                Toast.makeText(FuelStationBasicDetails.this,"Availabilities set successfully",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        builder.setView(view)
//                .setTitle("Set Availabilities");
//
//        dialog = builder.create();
//
//    }
}