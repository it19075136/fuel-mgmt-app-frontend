package com.example.fuel_mgmt_app_frontend.FuelStation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fuel_mgmt_app_frontend.DBHelper;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.SelectRegisrationType;
import com.example.fuel_mgmt_app_frontend.User.MainActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FuelStationList extends AppCompatActivity {

    MaterialButton newFuelStation;
    ImageView backIcon,signOutIcon;
    DBHelper DB;
    Intent intent;

    RecyclerView recyclerView;
    ArrayList<StationModel> fuelStations = new ArrayList<StationModel>();
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_list);

        newFuelStation = findViewById(R.id.newFuelStation);
        backIcon =  findViewById(R.id.left_icon);
        signOutIcon = findViewById(R.id.right_icon);
        DB = new DBHelper(this);

        recyclerView = findViewById(R.id.recycler_view);

        getFuelStationsList();

        newFuelStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(FuelStationList.this, FuelStationBasicDetails.class);
                startActivity(intent);
            }
        });
        signOutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DB.logedOut()){
                    intent = new Intent(FuelStationList.this, MainActivity.class);
                    Toast.makeText(FuelStationList.this, "Log out succesfuly", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(FuelStationList.this, "Log out Unsuccesfuly", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getFuelStationsList() {

        StationModel model = new StationModel();
        //loop and add names  in heres
//        model.setStationName();
        //call api before this
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MainAdapter(this,fuelStations);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}