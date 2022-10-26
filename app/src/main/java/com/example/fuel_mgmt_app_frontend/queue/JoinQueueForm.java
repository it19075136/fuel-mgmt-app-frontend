package com.example.fuel_mgmt_app_frontend.queue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuel_mgmt_app_frontend.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class JoinQueueForm extends AppCompatActivity {

    MaterialTextView stationName;
    Spinner vehicleType;
    MaterialButton joinNowBtn;
    String stationId = "6356b604dc7368b840073205";

    private static final String[] VEHICLE_TYPES = new String[] {
            "SEDAN", "HATCHBACK", "T-WHEEL", "SUV", "VAN/LIGHT LORRY", "BUS/HEAVY LORRY", "MOTORBIKE"
    };

    String SCHEDULE_ENDPOINT = "https://fuely-api.herokuapp.com/api/schedule";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue_form);

        vehicleType = findViewById(R.id.vehicleType);
        stationName = findViewById(R.id.stationName);
        joinNowBtn = findViewById(R.id.joinNow);

        stationName.setText(getIntent().getStringExtra("stationName"));

        //setting vehicle type dropdown list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, VEHICLE_TYPES);
        vehicleType.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        joinNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                create schedule
                createSchedule("avanthac@yahoo.com");
            }
        });

    }

//    set arrival time
    public void createSchedule(String loggedInEmail){

        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("email", loggedInEmail);
            requestBody.put("stationId", stationId);
            requestBody.put("vehicleType", vehicleType.getSelectedItem().toString());
            requestBody.put("arrival", Calendar.getInstance().getTime().toString());
        } catch (JSONException e) {
            Toast.makeText(this,"Input value error",Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
                Request.Method.POST,
                SCHEDULE_ENDPOINT,
                requestBody,
                (Response.Listener<JSONObject>) response -> {
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            Toast.makeText(JoinQueueForm.this,"Successfully joined the queue: "+response.getString("email"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JoinQueueForm.this,"Failed to join the queue",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }


}