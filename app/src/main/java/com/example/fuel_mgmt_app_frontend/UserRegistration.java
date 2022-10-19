package com.example.fuel_mgmt_app_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserRegistration extends AppCompatActivity{

    EditText emailEditText,passwordEditText,rePasswordEditText;
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
                            "Password do not match", Toast.LENGTH_SHORT).show();
                }else if(DB.checkEmail(email)){
                    Toast.makeText(UserRegistration.this, "User Name is already exits", Toast.LENGTH_SHORT).show();
                }else if(DB.insertData(email,password)){
                    Toast.makeText(UserRegistration.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserRegistration.this,SelectRegisrrationType.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UserRegistration.this, "Registration Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}