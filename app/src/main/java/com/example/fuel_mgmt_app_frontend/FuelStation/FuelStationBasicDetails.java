package com.example.fuel_mgmt_app_frontend.FuelStation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuel_mgmt_app_frontend.DBHelper;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.User.MainActivity;
import com.example.fuel_mgmt_app_frontend.queue.JoinQueueForm;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FuelStationBasicDetails extends AppCompatActivity {

    MaterialButton availabilityBtn, availSubmitButton, proceedBtn;
    ToggleButton existPetrolTgl, existDieselTgl, existSuperDieselTgl, existSuperPetrolTgl;
    HashMap<String,Boolean> availabilities;
    boolean availabilityCheck = false;
    Intent intent;
    ImageView backIcon,signOutIcon;
    DBHelper DB;
    AlertDialog dialog;
    EditText locationEditText,stationNameEditText,fuelArrivalTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_basic_details);

        availabilityBtn = findViewById(R.id.availability);
        proceedBtn = findViewById(R.id.next);
        backIcon =  findViewById(R.id.left_icon);
        signOutIcon = findViewById(R.id.right_icon);
        locationEditText = findViewById(R.id.location);
        stationNameEditText = findViewById(R.id.name);
        fuelArrivalTimeEditText =  findViewById(R.id.arrival);
        DB = new DBHelper(this);
        availabilities = new HashMap<>();
        RequestQueue requestQueue = Volley.newRequestQueue(FuelStationBasicDetails.this);

        createDialog();
        fuelArrivalTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(fuelArrivalTimeEditText);
            }
        });

        signOutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DB.logedOut()){

                    intent = new Intent(FuelStationBasicDetails.this, MainActivity.class);
                    Toast.makeText(FuelStationBasicDetails.this, "Log out succesfuly", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(FuelStationBasicDetails.this, "Log out Unsuccesfuly", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
                    String stationName = stationNameEditText.getText().toString();
                    String location = locationEditText.getText().toString();
                    String datetime = "20"+fuelArrivalTimeEditText.getText().toString();//2022-10-26T05:02:25.825Z
                    String[] splitdate=datetime.split(" ");
                    String arrivaleTime=splitdate[0]+"T"+splitdate[1];
//                    String strDate = "2013-05-15T10:00:00-0700";
                    String url = "https://fuely-api.herokuapp.com/api/fuelstation";
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//                    Date arrivaleTime = null;
//                    try {
//                        arrivaleTime = dateFormat.parse(date);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    Log.d("date", String.valueOf(date));//2022-10-28T21:34
//                    Log.d("arrivaleTime", String.valueOf(arrivaleTime));
//                    System.out.println(arrivaleTime);
                    if(DB.logingEmail()!=null){
                        createFuelStation(url,stationName,location, availabilities,arrivaleTime,requestQueue,DB.logingEmail());
                    }



//                    AppController.getInstance()
//                            .addToRequestQueue(jsonObjReq, tag_json_obj);
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
    public void createFuelStation(String url,String station,String location,HashMap<String,Boolean> availabilities,String arrivaleTime,RequestQueue requestQueue,String email){
//        Map<String,String> params = new HashMap();
//        for (int i = 0; i < availabilities.size(); i++) {
//            params.put(availabilities.ke)
//        }


        //                StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                url,
//                response -> Toast.makeText(FuelStationBasicDetails.this, "success", Toast.LENGTH_SHORT).show(),
//                error -> Toast.makeText(FuelStationBasicDetails.this, "error", Toast.LENGTH_SHORT).show()){
//            @Override
//            protected Map getParams() throws AuthFailureError {
//                Map params = new HashMap();
//                params.put("location", location);
//                params.put("stationName", station);
//                params.put("fuelAvailability",availabilities);
//                params.put("fuelArrivalTime",arrivaleTime);
//                params.put("fuelFinishTime",arrivaleTime);
//                params.put("email","sandungwp@gmail.com");
//                Log.d("hashparams", String.valueOf(params));
//                return params;
//            }
//        };
//
//        requestQueue.add(stringRequest);

//        String tag_json_obj = "json_obj_req";
//
//        String
//                url
//                = "https:// api.xyz.info/volley/person_object.json";

//        ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...PLease wait");
//        pDialog.show();

//        JsonObjectRequest
//                jsonObjReq
//                = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                null,
//                new Response.Listener() {
//
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {
//                        Log.d("TAG", response.toString());
////                        pDialog.hide();
//                    }
//                },
//                new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        VolleyLog.d("TAG", "Error: "
//                                + error.getMessage());
////                        pDialog.hide();
//                    }
//                }) {
//
//            @Override
//            protected Map getParams()
//            {
//                Map params = new HashMap();
//                params.put("location", location);
//                params.put("stationName", station);
//                params.put("fuelAvailability",availabilities);
//                params.put("fuelArrivalTime",arrivaleTime);
//                params.put("fuelFinishTime",arrivaleTime);
//                params.put("email","sandungwp@gmail.com");
//                Log.d("hashparams", String.valueOf(params));
//
//                return params;
//            }
//
//        };
//
//        AppController.getInstance()
//                .addToRequestQueue(jsonObjReq, tag_json_obj);


        JSONObject requestBody = new JSONObject();
        JSONObject requestBody1 = new JSONObject();

        try {
            for (String i:availabilities.keySet()) {
                requestBody1.put(i,availabilities.get(i));
            }

            requestBody.put("location", location);
            requestBody.put("stationName", station);
            requestBody.put("fuelAvailability", requestBody1);
            requestBody.put("fuelArrivalTime", arrivaleTime);
            requestBody.put("fuelFinishTime", arrivaleTime);
            requestBody.put("email",email);
//            requestBody.put("arrival", Calendar.getInstance().getTime().toString());
        } catch (JSONException e) {
            Toast.makeText(this,"Input value error",Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                (Response.Listener<JSONObject>) response -> {
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            Toast.makeText(FuelStationBasicDetails.this,"Successfully created: "+response.getString("email"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FuelStationBasicDetails.this,"Failed to create",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void showDateTimeDialog(EditText timeSetText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY,i);
                        calendar.set(Calendar.MINUTE,i1);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        timeSetText.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                };
                new TimePickerDialog(FuelStationBasicDetails.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        };
        new DatePickerDialog(FuelStationBasicDetails.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}