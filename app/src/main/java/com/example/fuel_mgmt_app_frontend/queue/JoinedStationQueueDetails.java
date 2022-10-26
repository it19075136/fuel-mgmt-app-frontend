package com.example.fuel_mgmt_app_frontend.queue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuel_mgmt_app_frontend.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class JoinedStationQueueDetails extends AppCompatActivity {

    String stationId;
    Intent intent;

    String SCHEDULE_ENDPOINT = "https://fuely-api.herokuapp.com/api/schedule";
    String FUEL_STATION_ENDPOINT = "https://fuely-api.herokuapp.com/api/fuelstation";
    RequestQueue requestQueue;

    MaterialButton moreDetailsBtn,completedBtn,exitBtn;
    MaterialTextView stationHeading;
    ImageView petrolstatus,superPetrolstatus,dieselstatus,superDieselstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_station_queue_details);

        stationId = getIntent().getStringExtra("station");

        petrolstatus = findViewById(R.id.petrolAvail);
        dieselstatus = findViewById(R.id.dieselAvail);
        superPetrolstatus = findViewById(R.id.superPetrolAvail);
        superDieselstatus = findViewById(R.id.superDieselAvail);
        stationHeading = findViewById(R.id.fillingStation);

        requestQueue = Volley.newRequestQueue(this);

        setAvailabilities(stationId);

    }

//    fuel available in station
    void setAvailabilities(String stationId){

        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
                Request.Method.GET,
                FUEL_STATION_ENDPOINT+"/".concat(stationId),
                null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        JSONObject availObj = (JSONObject) response.get("fuelAvailability");
                        if (!(boolean) availObj.get("petrol"))
                            petrolstatus.setImageDrawable(getDrawable(R.drawable.red_cross));
                        if (!(boolean) availObj.get("super petrol"))
                            superPetrolstatus.setImageDrawable(getDrawable(R.drawable.red_cross));
                        if (!(boolean) availObj.get("diesel"))
                            dieselstatus.setImageDrawable(getDrawable(R.drawable.red_cross));
                        if (!(boolean) availObj.get("super diesel"))
                            superDieselstatus.setImageDrawable(getDrawable(R.drawable.red_cross));
                        stationHeading.setText((String) response.get("stationName"));
                    } catch (JSONException e) {
                        Toast.makeText(JoinedStationQueueDetails.this,"Data error,please contact administrator: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JoinedStationQueueDetails.this,"Failed to load the station details - "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

//  schedules in progress for the station
    void setPosition(){

    }

}