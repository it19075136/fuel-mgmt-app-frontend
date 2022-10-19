package com.example.fuel_mgmt_app_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FuelStationRegistration extends AppCompatActivity{

    EditText emailEditText,passwordEditText,rePasswordEditText,fuelStationNameEditText,districtEditText;
    TextView loginText;
    Button signupbtn;
    DBHelper DB;
    String[] item = {"kandy","Colombo","Ampara"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItem;
    String district;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_registration);

        emailEditText =  findViewById(R.id.email);
        passwordEditText =  findViewById(R.id.password);
        rePasswordEditText =  findViewById(R.id.RePassword);
        fuelStationNameEditText =  findViewById(R.id.fuelStationName);
        districtEditText =  findViewById(R.id.district);
        signupbtn =  findViewById(R.id.signup);
        autoCompleteTxt =  findViewById(R.id.auto_complete);

        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item,item);
        autoCompleteTxt.setAdapter(adapterItem);

        DB = new DBHelper(this);

        loginText = findViewById(R.id.AlreadyHaveAccount);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(FuelStationRegistration.this,MainActivity.class);
                startActivity(intent);
            }
        });
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                district = adapterView.getItemAtPosition(i).toString();
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String rePassword = rePasswordEditText.getText().toString();
                String fuelStationName = fuelStationNameEditText.getText().toString();


                if(email.equals("")||password.equals("")||rePassword.equals("")||fuelStationName.equals("")||district.equals("")){
                    Toast.makeText(FuelStationRegistration.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(rePassword)){
                    Toast.makeText(FuelStationRegistration.this, "Password do not match", Toast.LENGTH_SHORT).show();
                }else if(DB.checkEmail(email)){
                    Toast.makeText(FuelStationRegistration.this, "User Name is already exits", Toast.LENGTH_SHORT).show();
                }else if(DB.insertData(email,password)){
                    Toast.makeText(FuelStationRegistration.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FuelStationRegistration.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(FuelStationRegistration.this, "Registration Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}