package com.example.fuel_mgmt_app_frontend.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuel_mgmt_app_frontend.DBHelper;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.SelectRegisrationType;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText emailEditText,passwordEditText;
    TextView signupText;
    Button loginbtn;
    DBHelper DB;


    ImageView showPass;

    private static final int HIDDEN_PASSWORD = 129;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signupText = findViewById(R.id.newUser);
        emailEditText =  findViewById(R.id.email);
        passwordEditText =  findViewById(R.id.password);
        loginbtn =  findViewById(R.id.login);
        DB = new DBHelper(this);

        if(DB.logingEmail()!=null){
            Intent intent =  new Intent(MainActivity.this,SelectRegisrationType.class);
            startActivity(intent);
        }

        showPass = findViewById(R.id.showPass);

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordEditText.getInputType() == HIDDEN_PASSWORD) {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPass.setImageDrawable(getDrawable(R.drawable.show_pass_24));
                }
                else {
                    passwordEditText.setInputType(HIDDEN_PASSWORD);
                    showPass.setImageDrawable(getDrawable(R.drawable.ic_hide));
                }
            }
        });


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this,UserRegistration.class);
                startActivity(intent);
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
                else if(DB.checkEmailPassword(email,password) && DB.loging(email)){
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(MainActivity.this, SelectRegisrationType.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}