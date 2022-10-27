package com.example.fuel_mgmt_app_frontend.FuelStation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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


public class FuelStationList extends AppCompatActivity implements RecyclerViewInterface {

    MaterialButton newFuelStation;
    ImageView signOutIcon,backIcon;
    DBHelper DB;
    Intent intent;

    RecyclerView recyclerView;
    ArrayList<StationModel> fuelStations = new ArrayList<StationModel>();
    ArrayList<String> fuelStationId  = new ArrayList<String>();
    MainAdapter adapter;

    StationModel model;

    String url = "https://fuely-api.herokuapp.com/api/fuelstation/byEmail/sandungwp@gmail.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_list);

        newFuelStation = findViewById(R.id.newFuelStation);
        signOutIcon = findViewById(R.id.right_icon);
        DB = new DBHelper(this);
        backIcon = findViewById(R.id.left_icon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                // yourMethod();
//                //finish();
//                //startActivity(getIntent());
//            }
//        }, 5000);
        getFuelStationsAPI(requestQueue);

        newFuelStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("StartedMessage", "newFuelStation.setOnClickListener started");

                Intent intent =  new Intent(FuelStationList.this, FuelStationBasicDetails.class);
                startActivity(intent);

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


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MainAdapter(this,fuelStations, this);

        recyclerView.setAdapter(adapter);

    }

    public void getFuelStationsAPI(RequestQueue requestQueue){
//        Toast.makeText(this,"test in",Toast.LENGTH_SHORT).show();

        String url = "https://fuely-api.herokuapp.com/api/fuelstation/byEmail/" + DB.logingEmail();
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
                            model = new StationModel();
                            try {

                                JSONObject responseObj = response.getJSONObject(i);

//                                String location = responseObj.getString("location");
                                String stationName = responseObj.getString("stationName");
                                String stationID = responseObj.getString("id");
//                                Log.d("location", location);
//                                Log.d("stationName", stationName);
                                model.setStationName(stationName);

                                fuelStationId.add(stationID);
                                fuelStations.add(model);

                                Log.d("fuel station id", fuelStationId.get(i));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        getFuelStationsList();

//                        for (int i = 0; i < response.length(); i++) {
//                            Log.d("fuelStations array loop", String.valueOf(fuelStations.get(i)));
//                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
        JSONArray jsonArray=getStationById(url);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void OnItemClick(int position) {
        Toast.makeText(this, "I've been clicked and id is = " + fuelStations.get(position).getStationName() + " & position is "+ position, Toast.LENGTH_SHORT).show();

        intent = new Intent(FuelStationList.this, FuelStationUpdate.class);

        intent.putExtra("id",fuelStationId.get(position));

        startActivity(intent);


    }
}