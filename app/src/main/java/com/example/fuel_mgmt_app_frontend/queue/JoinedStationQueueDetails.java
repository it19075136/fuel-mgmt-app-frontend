package com.example.fuel_mgmt_app_frontend.queue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.SelectRegisrationType;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class JoinedStationQueueDetails extends AppCompatActivity {

    String stationId,scheduleId;
    Intent intent;
    JSONObject schedule;
    boolean isPumped = false;

    String SCHEDULE_ENDPOINT = "https://fuely-api.herokuapp.com/api/schedule";
    String FUEL_STATION_ENDPOINT = "https://fuely-api.herokuapp.com/api/fuelstation";
    RequestQueue requestQueue;

    MaterialButton moreDetailsBtn,completedBtn,exitBtn,closeDialog;
    MaterialTextView stationHeading,positionNumber,sedanCount,hatchCount,vanCount,busCount,twCount,motorCount,suvCount;
    ImageView petrolstatus,superPetrolstatus,dieselstatus,superDieselstatus;
    AlertDialog dialog;

    int MOTORBIKE,BUS_LORRY,VAN_LORRY,SUV,T_WHEEL,HATCHBACK,SEDAN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_station_queue_details);

        Toast.makeText(this,"Please wait...",Toast.LENGTH_SHORT).show();

        stationId = getIntent().getStringExtra("station");
        try {
            schedule = new JSONObject(getIntent().getStringExtra("schedule"));
        } catch (JSONException e) {
            Toast.makeText(this,"Failed to load schedule, try again",Toast.LENGTH_SHORT).show();
        }

        petrolstatus = findViewById(R.id.petrolAvail);
        dieselstatus = findViewById(R.id.dieselAvail);
        superPetrolstatus = findViewById(R.id.superPetrolAvail);
        superDieselstatus = findViewById(R.id.superDieselAvail);

        stationHeading = findViewById(R.id.fillingStation);
        positionNumber = findViewById(R.id.positionNumber);

        completedBtn = findViewById(R.id.completed);
        moreDetailsBtn = findViewById(R.id.moreDetails);
        exitBtn = findViewById(R.id.exit);

        moreDetailsBtn.setEnabled(false);

        requestQueue = Volley.newRequestQueue(this);

        setAvailabilities(stationId);
        setPosition();

        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPumped = true;
                updateSchedule();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPumped = false;
                updateSchedule();
            }
        });

        moreDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                vehicle types modal in station
                dialog.show();
            }
        });
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

        JsonArrayRequest jsonObjectRequest  = new JsonArrayRequest(
                Request.Method.GET,
                SCHEDULE_ENDPOINT+"/nowInStation/".concat(stationId),
                null,
                (Response.Listener<JSONArray>) response -> {
                    positionNumber.setText(String.valueOf(response.length()));
                    createDialog(response);
                    moreDetailsBtn.setEnabled(true);
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JoinedStationQueueDetails.this,"Position fetching failed - "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

//    update schedule on exit/pumped
    void updateSchedule(){

        try {
            schedule.put("departure",Calendar.getInstance().getTime().toString());
            schedule.put("isPumped",isPumped);
        } catch (JSONException e) {
            Toast.makeText(JoinedStationQueueDetails.this,"Failed to set the values - "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        try {
            scheduleId =  schedule.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
                Request.Method.PUT,
                SCHEDULE_ENDPOINT+"/".concat(scheduleId),
                schedule,
                (Response.Listener<JSONObject>) response -> {
                    Toast.makeText(this,"Action Successful",Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, SelectRegisrationType.class);
                    startActivity(intent);
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JoinedStationQueueDetails.this,"Action failed - "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void createDialog(JSONArray queueList) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.custom_dialog_queue_list,null);
        closeDialog = view.findViewById(R.id.close);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        JSONObject responseObj;

        for (int i = 0; i < queueList.length(); i++) {

            try {
                responseObj = queueList.getJSONObject(i);
                switch (responseObj.getString("vehicleType")){
                    case "MOTORBIKE":
                        MOTORBIKE++;
                        break;
                    case "BUS/HEAVY LORRY":
                        BUS_LORRY++;
                        break;
                    case "VAN/LIGHT LORRY":
                        VAN_LORRY++;
                        break;
                    case "SUV":
                        SUV++;
                        break;
                    case "T-WHEEL":
                        T_WHEEL++;
                        break;
                    case "HATCHBACK":
                        HATCHBACK++;
                        break;
                    case "SEDAN":
                        SEDAN++;
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        sedanCount = view.findViewById(R.id.sedanCount);
        sedanCount.setText(String.valueOf(SEDAN));
        vanCount = view.findViewById(R.id.vanCount);
        vanCount.setText(String.valueOf(VAN_LORRY));
        busCount = view.findViewById(R.id.busCount);
        busCount.setText(String.valueOf(BUS_LORRY));
        hatchCount = view.findViewById(R.id.hatchCount);
        hatchCount.setText(String.valueOf(HATCHBACK));
        twCount = view.findViewById(R.id.twCount);
        twCount.setText(String.valueOf(T_WHEEL));
        motorCount = view.findViewById(R.id.motorbikeCount);
        motorCount.setText(String.valueOf(MOTORBIKE));
        suvCount = view.findViewById(R.id.suvCount);
        suvCount.setText(String.valueOf(SUV));

        builder.setView(view)
                .setTitle("Vehicle types in queue");

        dialog = builder.create();

    }

}