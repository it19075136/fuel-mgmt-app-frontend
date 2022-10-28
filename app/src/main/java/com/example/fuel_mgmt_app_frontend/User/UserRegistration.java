package com.example.fuel_mgmt_app_frontend.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuel_mgmt_app_frontend.DBHelper;
import com.example.fuel_mgmt_app_frontend.FuelStation.FuelStationBasicDetails;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.SelectRegisrationType;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegistration extends AppCompatActivity {

    TextInputEditText emailEditText, passwordEditText, rePasswordEditText;
    Button signupbtn;
    TextView loginText;
    DBHelper DB;
    String emailMon, passwordMon, responseIn;
    Boolean internetOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

//        emailEditText =  findViewById(R.id.email);
//        passwordEditText =  findViewById(R.id.password);
//        rePasswordEditText =  findViewById(R.id.RePassword);
//        signupbtn =  findViewById(R.id.signup);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        rePasswordEditText = findViewById(R.id.RePassword);
        signupbtn = findViewById(R.id.signup);

        loginText = findViewById(R.id.AlreadyHaveAccount);
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegistration.this);
        DB = new DBHelper(this);
        if (DB.logingEmail() != null) {
            Intent intent = new Intent(UserRegistration.this, SelectRegisrationType.class);
            startActivity(intent);
        }
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegistration.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String rePassword = rePasswordEditText.getText().toString();


                if (email.equals("") || password.equals("") || rePassword.equals("")) {
                    Toast.makeText(UserRegistration.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(UserRegistration.this,
                            "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (DB.checkEmail(email)) {
                    Toast.makeText(UserRegistration.this, "User already exists", Toast.LENGTH_SHORT).show();
                } else if (true) {
//                    userGEtByEmail("https://fuely-api.herokuapp.com/api/Users/byEmail/"+email,requestQueue);
//                    userGEtByEmail("https://localhost:7087/api/Users/byEmail/"+email,requestQueue);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            "https://fuely-api.herokuapp.com/api/Users/byEmail/" + email,
                            null,
                            (Response.Listener<JSONObject>) response -> {
                                try {
//                        if(){
//
//                        }
                                    emailMon = (String) response.get("email");
                                    passwordMon = (String) response.get("password");
                                    Log.d("emailMon ", emailMon);
                                    if (emailMon.equals("UserNotFound") && passwordMon.equals("UserNotFound")) {
                                        if (DB.insertData(email, password) && DB.loging(email)) {
                                            String url = "https://fuely-api.herokuapp.com/api/users";
                                            userAddToMongo(email, password, url, requestQueue);
                                            Toast.makeText(UserRegistration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UserRegistration.this, SelectRegisrationType.class);
                                            startActivity(intent);
                                        }
                                    } else if (emailMon.equals(email) && passwordMon.equals(password)) {
                                        Log.d("emailMon.equalspassword", "emailMon.equalspassword");
                                        if (DB.insertData(email, password) && DB.loging(email)) {
//                                            String url = "https://fuely-api.herokuapp.com/api/users";
////                                            userAddToMongo(email, password, url, requestQueue);
                                            Toast.makeText(UserRegistration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UserRegistration.this, SelectRegisrationType.class);
                                            startActivity(intent);
                                        }
                                    } else if (emailMon.equals(email)) {
                                        Log.d("emailMonnotequal ", "emailMonnotequal");
                                        DB.insertData(emailMon, passwordMon);
                                        Toast.makeText(UserRegistration.this, "email is already exists", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(UserRegistration.this, MainActivity.class);
//                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                internetOn = true;
                                Log.d("internetOn ", String.valueOf(internetOn));
//                    JSONObject jsonObject;
//                    jsonObject = response.getJSONObject();
//                    stationModel.setStationId(jsonObject.getString("id"));
//                    stationModel.setLocation(jsonObject.getString("location"));
//                                Toast.makeText(UserRegistration.this,"Successfully created",Toast.LENGTH_SHORT).show();
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UserRegistration.this, "internet is required", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);

                    Log.d("userGEtByEmail ", "userGEtByEmail");

//                    if(internetOn){
                    Log.d("internetOnif ", String.valueOf(internetOn));
//                        Log.d("emailMon.equals", String.valueOf(emailMon.equals("")));

//                    }

                } else {
                    Toast.makeText(UserRegistration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void userAddToMongo(String email, String password, String url, RequestQueue requestQueue) {
        JSONObject requestBody = new JSONObject();
//        JSONObject requestBody1 = new JSONObject();

        try {
//            for (String i:availabilities.keySet()) {
//                requestBody1.put(i,availabilities.get(i));
//            }

            requestBody.put("password", password);
            requestBody.put("email", email);
//            requestBody.put("fuelAvailability", requestBody1);
//            requestBody.put("fuelArrivalTime", arrivaleTime);
//            requestBody.put("fuelFinishTime", arrivaleTime);
//            requestBody.put("email",email);
//            requestBody.put("arrival", Calendar.getInstance().getTime().toString());
        } catch (JSONException e) {
            Toast.makeText(this, "Input value error", Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                (Response.Listener<JSONObject>) response -> {
                    Toast.makeText(UserRegistration.this, "Successfully created", Toast.LENGTH_SHORT).show();
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserRegistration.this, "internet is required", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void userGEtByEmail(String url, RequestQueue requestQueue) {
//        JSONObject requestBody = new JSONObject();
//        JSONObject requestBody1 = new JSONObject();

//        try {
//            for (String i:availabilities.keySet()) {
//                requestBody1.put(i,availabilities.get(i));
//            }

//            requestBody.put("password", password);
//            requestBody.put("email", email);
//            requestBody.put("fuelAvailability", requestBody1);
//            requestBody.put("fuelArrivalTime", arrivaleTime);
//            requestBody.put("fuelFinishTime", arrivaleTime);
//            requestBody.put("email",email);
//            requestBody.put("arrival", Calendar.getInstance().getTime().toString());
//        } catch (JSONException e) {
//            Toast.makeText(this,"Input value error",Toast.LENGTH_SHORT).show();
//        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (Response.Listener<JSONObject>) response -> {
                    try {
//                        if(){
//
//                        }
                        emailMon = (String) response.get("email");
                        passwordMon = (String) response.get("password");
                        Log.d("emailMon ", emailMon);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    internetOn = true;
                    Log.d("internetOn ", String.valueOf(internetOn));
//                    JSONObject jsonObject;
//                    jsonObject = response.getJSONObject();
//                    stationModel.setStationId(jsonObject.getString("id"));
//                    stationModel.setLocation(jsonObject.getString("location"));
                    Toast.makeText(UserRegistration.this, "Successfully created", Toast.LENGTH_SHORT).show();
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserRegistration.this, "unsuccesfull is required", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}

