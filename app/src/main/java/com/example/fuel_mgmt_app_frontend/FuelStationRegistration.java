package com.example.fuel_mgmt_app_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FuelStationRegistration extends AppCompatActivity{

    EditText emailEditText,passwordEditText,fuelStationNameEditText,districtEditText;
    TextView loginText,Diesel_arrival_time_txt,Diesel_finish_time_txt,Diesel_super_arrival_time_txt,Diesel_super_finish_time_txt,Petrol92_arrival_time_txt,Petrol92_finish_time_txt,Petrol95_arrival_time_txt,Petrol95_finish_time_txt;
    Button signupbtn,Diesel_arrival_time_btn,Diesel_finish_time_btn,Diesel_super_arrival_time_btn,Diesel_super_finish_time_btn,Petrol92_arrival_time_btn,Petrol92_finish_time_btn,Petrol95_arrival_time_btn,Petrol95_finish_time_btn;

    DBHelper DB;
    String[] item = {"kandy","Colombo","Ampara"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItem;
    String district;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_registration);

//        emailEditText =  findViewById(R.id.email);
//        passwordEditText =  findViewById(R.id.password);
        fuelStationNameEditText =  findViewById(R.id.fuelStationName);
//        districtEditText =  findViewById(R.id.district);
        signupbtn =  findViewById(R.id.signup);
//        autoCompleteTxt =  findViewById(R.id.auto_complete);

        Diesel_arrival_time_txt =  findViewById(R.id.Diesel_arrival_time_txt);
        Diesel_finish_time_txt=  findViewById(R.id.Diesel_finish_time_txt);
        Diesel_super_arrival_time_txt=  findViewById(R.id.Diesel_super_arrival_time_txt);
        Diesel_super_finish_time_txt=  findViewById(R.id.Diesel_super_finish_time_txt);
        Petrol92_arrival_time_txt= findViewById(R.id.Petrol92_arrival_time_txt);
        Petrol92_finish_time_txt=  findViewById(R.id.Petrol92_finish_time_txt);
        Petrol95_arrival_time_txt=  findViewById(R.id.Petrol95_arrival_time_txt);
        Petrol95_finish_time_txt=  findViewById(R.id.Petrol95_finish_time_txt);

        Diesel_arrival_time_txt.setInputType(InputType.TYPE_NULL);
        Diesel_finish_time_txt.setInputType(InputType.TYPE_NULL);
        Diesel_super_arrival_time_txt.setInputType(InputType.TYPE_NULL);
        Diesel_super_finish_time_txt.setInputType(InputType.TYPE_NULL);
        Petrol92_arrival_time_txt.setInputType(InputType.TYPE_NULL);
        Petrol92_finish_time_txt.setInputType(InputType.TYPE_NULL);
        Petrol95_arrival_time_txt.setInputType(InputType.TYPE_NULL);
        Petrol95_finish_time_txt.setInputType(InputType.TYPE_NULL);

        Diesel_arrival_time_btn=  findViewById(R.id.Diesel_arrival_time_btn);
        Diesel_finish_time_btn=  findViewById(R.id.Diesel_finish_time_btn);
        Diesel_super_arrival_time_btn=  findViewById(R.id.Diesel_super_arrival_time_btn);
        Diesel_super_finish_time_btn=  findViewById(R.id.Diesel_super_finish_time_btn);
        Petrol92_arrival_time_btn=  findViewById(R.id.Petrol92_arrival_time_btn);
        Petrol92_finish_time_btn=  findViewById(R.id.Petrol92_finish_time_btn);
        Petrol95_arrival_time_btn=  findViewById(R.id.Petrol95_arrival_time_btn);
        Petrol95_finish_time_btn=  findViewById(R.id.Petrol95_finish_time_btn);

        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item,item);
//        autoCompleteTxt.setAdapter(adapterItem);

        DB = new DBHelper(this);

//        loginText = findViewById(R.id.AlreadyHaveAccount);

        Diesel_arrival_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Diesel_arrival_time_txt);
            }
        });

        Diesel_finish_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Diesel_finish_time_txt);
            }
        });
        Diesel_super_arrival_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Diesel_super_arrival_time_txt);
            }
        });
        Diesel_super_finish_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Diesel_super_finish_time_txt);
            }
        });
        Petrol92_arrival_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Petrol92_arrival_time_txt);
            }
        });
        Petrol92_finish_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Petrol92_finish_time_txt);
            }
        });
        Petrol95_arrival_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Petrol95_arrival_time_txt);
            }
        });
        Petrol95_finish_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Petrol95_finish_time_txt);
            }
        });


//        loginText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =  new Intent(FuelStationRegistration.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                district = adapterView.getItemAtPosition(i).toString();
//            }
//        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String fuelStationName = fuelStationNameEditText.getText().toString();


//                if(email.equals("")||password.equals("")||fuelStationName.equals("")||district.equals("")){
//                    Toast.makeText(FuelStationRegistration.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
//                } else if(DB.checkEmail(email)){
//                    Toast.makeText(FuelStationRegistration.this, "User Name is already exits", Toast.LENGTH_SHORT).show();
//                }else if(DB.insertData(email,password)){
//                    Toast.makeText(FuelStationRegistration.this, "Registration successfull", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(FuelStationRegistration.this,MainActivity.class);
//                    startActivity(intent);
//                }
//                else{
//                    Toast.makeText(FuelStationRegistration.this, "Registration Unsuccessfull", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private void showDateTimeDialog(TextView timeSetText) {
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
                new TimePickerDialog(FuelStationRegistration.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        };
        new DatePickerDialog(FuelStationRegistration.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}