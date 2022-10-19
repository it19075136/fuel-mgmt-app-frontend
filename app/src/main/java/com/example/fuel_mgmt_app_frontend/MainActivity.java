package com.example.fuel_mgmt_app_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText,passwordEditText;
    TextView signupText;
    Button loginbtn;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signupText = findViewById(R.id.newUser);
        emailEditText =  findViewById(R.id.email);
        passwordEditText =  findViewById(R.id.password);
        loginbtn =  findViewById(R.id.login);
        DB = new DBHelper(this);


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this,UserRegistration.class);
                startActivity(intent);
//                String email = emailEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//
//
//                if(email.equals("")||password.equals("")){
//                    Toast.makeText(MainActivity.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
//                }
//                else if(DB.checkEmailPassword(email,password)){
//                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                    Intent intent =  new Intent(MainActivity.this,SelectRegisrrationType.class);
//                    startActivity(intent);
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "Login Unsuccessfull", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if(email.equals("")||password.equals("")){
                    Toast.makeText(MainActivity.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                }
                else if(DB.checkEmailPassword(email,password)){
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(MainActivity.this,SelectRegisrrationType.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Login Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}