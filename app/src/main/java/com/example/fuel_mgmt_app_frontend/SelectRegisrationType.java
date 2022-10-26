package com.example.fuel_mgmt_app_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fuel_mgmt_app_frontend.FuelStation.FuelStationList;
import com.example.fuel_mgmt_app_frontend.User.MainActivity;
import com.example.fuel_mgmt_app_frontend.User.UserRegistration;
import com.example.fuel_mgmt_app_frontend.queue.JoinQueueSearchList;

public class SelectRegisrationType extends AppCompatActivity {

    Button userTypebtn,fuelStationTypebtn;
    ImageView backIcon,signOutIcon;
    DBHelper DB;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_regisration_type);

        userTypebtn = findViewById(R.id.UserType);
        fuelStationTypebtn = findViewById(R.id.FuelStationType);
        backIcon =  findViewById(R.id.left_icon);
        signOutIcon = findViewById(R.id.right_icon);
        DB = new DBHelper(this);

        signOutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DB.logedOut()){
                    intent = new Intent(SelectRegisrationType.this, MainActivity.class);
                    Toast.makeText(SelectRegisrationType.this, "Logout successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SelectRegisrationType.this, "Logout failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        userTypebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(SelectRegisrationType.this, JoinQueueSearchList.class);
                startActivity(intent);
            }
        });

        fuelStationTypebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(SelectRegisrationType.this, FuelStationList.class);
                startActivity(intent);
            }
        });
    }
}