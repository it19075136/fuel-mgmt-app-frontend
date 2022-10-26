package com.example.fuel_mgmt_app_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fuel_mgmt_app_frontend.User.MainActivity;

public class toolbarActions extends AppCompatActivity {

    ImageView signOutIcon;
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);

        DB = new DBHelper(this);
        signOutIcon = findViewById(R.id.right_icon);

        signOutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DB.logedOut()){
                    Intent intent = new Intent(toolbarActions.this, MainActivity.class);
                    Toast.makeText(toolbarActions.this, "Log out successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(toolbarActions.this, "Log out Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
