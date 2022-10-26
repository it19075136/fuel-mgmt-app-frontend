package com.example.fuel_mgmt_app_frontend.queue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fuel_mgmt_app_frontend.R;

public class JoinQueueSearchList extends AppCompatActivity {

    SearchView searchView;
    Button joinBtn;
    String stationName = "Jayasiri";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue_search_list);

        searchView = findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(false);
        joinBtn = findViewById(R.id.joinBtn);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                trigger search with station name
                stationName = s;
                Toast.makeText(JoinQueueSearchList.this, "search text submit: "+s,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(JoinQueueSearchList.this, JoinQueueForm.class);
                intent.putExtra("stationName", stationName);
                startActivity(intent);
            }
        });

    }
}