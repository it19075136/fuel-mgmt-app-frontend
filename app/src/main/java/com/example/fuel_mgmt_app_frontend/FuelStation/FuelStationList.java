package com.example.fuel_mgmt_app_frontend.FuelStation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuel_mgmt_app_frontend.DBHelper;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.SelectRegisrationType;
import com.example.fuel_mgmt_app_frontend.User.MainActivity;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FuelStationList extends AppCompatActivity {

    MaterialButton newFuelStation;
    ImageView signOutIcon;
    DBHelper DB;
    Intent intent;

    RecyclerView recyclerView;
    ArrayList<StationModel> fuelStations = new ArrayList<StationModel>();
    MainAdapter adapter;

    StationModel model  = new StationModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_list);

        newFuelStation = findViewById(R.id.newFuelStation);
        signOutIcon = findViewById(R.id.right_icon);
        DB = new DBHelper(this);


        recyclerView = findViewById(R.id.recycler_view);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        newFuelStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("StartedMessage", "newFuelStation.setOnClickListener started");
//                String email = DB.logingEmail();
//                Log.d("loged email", email);
                String url = "https://fuely-api.herokuapp.com/api/fuelstation/byEmail/sandungwp@gmail.com";
//                url += email;

                JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
//                                res[0] = response;
                                for (int i = 0; i < response.length(); i++) {

                                    try {

                                        JSONObject responseObj = response.getJSONObject(i);

                                        String location = responseObj.getString("location");
                                        String stationName = responseObj.getString("stationName");
                                        Log.d("location", location);
                                        Log.d("stationName", stationName);
                                        model.setStationName(stationName);

                                        fuelStations.add(model);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Log.d("fuelStations Array size", String.valueOf(fuelStations.size()));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonArrayRequest);
                JSONArray jsonArray=getStationById(url);
                Intent intent =  new Intent(FuelStationList.this, FuelStationBasicDetails.class);
                startActivity(intent);

                getFuelStationsList();

            }
        });
        signOutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DB.logedOut()){
                    intent = new Intent(FuelStationList.this, MainActivity.class);
                    Toast.makeText(FuelStationList.this, "Logout successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(FuelStationList.this, "Logout failed", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public JSONArray getStationById(String url){
         JSONArray[] res = new JSONArray[1];
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        res[0] = response;
                        for (int i = 0; i < response.length(); i++) {

                            try {

                                JSONObject responseObj = response.getJSONObject(i);

                                String location = responseObj.getString("location");
                                String stationName = responseObj.getString("stationName");
                                Log.d("location", location);
                                Log.d("stationName", stationName);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
        return res[0];
    }

    private void getFuelStationsList() {

//        StationModel model = new StationModel();
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