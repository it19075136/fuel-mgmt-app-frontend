package com.example.fuel_mgmt_app_frontend.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuel_mgmt_app_frontend.DBHelper;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.SelectRegisrationType;
import com.google.android.material.textfield.TextInputEditText;

public class UserRegistration extends AppCompatActivity{

    TextInputEditText emailEditText,passwordEditText,rePasswordEditText;
    Button signupbtn;
    TextView loginText;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        emailEditText =  findViewById(R.id.email);
        passwordEditText =  findViewById(R.id.password);
        rePasswordEditText =  findViewById(R.id.RePassword);
        signupbtn =  findViewById(R.id.signup);
        loginText = findViewById(R.id.AlreadyHaveAccount);
        DB = new DBHelper(this);
        if(DB.logingEmail()!=null){
            Intent intent =  new Intent(UserRegistration.this,SelectRegisrationType.class);
            startActivity(intent);
        }
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(UserRegistration.this,MainActivity.class);
                startActivity(intent);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String rePassword = rePasswordEditText.getText().toString();

                if(email.equals("")||password.equals("")||rePassword.equals("")){
                    Toast.makeText(UserRegistration.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(rePassword)){
                    Toast.makeText(UserRegistration.this,
                            "Passwords do not match", Toast.LENGTH_SHORT).show();
                }else if(DB.checkEmail(email)){
                    Toast.makeText(UserRegistration.this, "User already exists", Toast.LENGTH_SHORT).show();
                }else if(DB.insertData(email,password)){
                    Toast.makeText(UserRegistration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserRegistration.this, SelectRegisrationType.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UserRegistration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}