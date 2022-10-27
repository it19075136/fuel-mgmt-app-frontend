package com.example.fuel_mgmt_app_frontend.queue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuel_mgmt_app_frontend.DBHelper;
import com.example.fuel_mgmt_app_frontend.FuelStation.StationModel;
import com.example.fuel_mgmt_app_frontend.R;
import com.example.fuel_mgmt_app_frontend.User.MainActivity;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JoinQueueSearchList extends AppCompatActivity {

    SearchView searchView;
    String stationName = "Jayasiri";
    Intent intent;
    ImageView signOutIcon,backIcon;
    DBHelper dbHelper;

    ListView listView;
    ArrayList<StationModel> list;
    CustomAdapter arrayAdapter;

    String FUEL_STATION_ENDPOINT = "https://fuely-api.herokuapp.com/api/fuelstation";
    RequestQueue requestQueue;

    MaterialTextView name,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue_search_list);

        searchView = findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(false);
        listView = findViewById(R.id.fuelStationList);
        signOutIcon = findViewById(R.id.right_icon);
        backIcon = findViewById(R.id.left_icon);
        dbHelper = new DBHelper(this);

        requestQueue = Volley.newRequestQueue(this);

        list = new ArrayList<>();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signOutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.logedOut()){
                    Intent intent = new Intent(JoinQueueSearchList.this, MainActivity.class);
                    Toast.makeText(JoinQueueSearchList.this, "Log out successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(JoinQueueSearchList.this, "Log out Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(JoinQueueSearchList.this, JoinQueueForm.class);
                intent.putExtra("stationName", list.get(i).getStationName());
                intent.putExtra("stationId", list.get(i).getStationId());
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                trigger search with station name
                stationName = s;
                Toast.makeText(JoinQueueSearchList.this, "please wait...",Toast.LENGTH_SHORT).show();
                getStationList(stationName);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

//    fetch and set stations list by search text
    void getStationList(String searchText){

        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest(
                Request.Method.GET,
                FUEL_STATION_ENDPOINT+"/bysearch/"+searchText,
                null,
                (Response.Listener<JSONArray>) response -> {
                    StationModel stationModel;
                    JSONObject jsonObject;
                    list.clear();
                    if(response.length() == 0)
                        Toast.makeText(this,"No matching fuel stations found",Toast.LENGTH_SHORT).show();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            stationModel = new StationModel();
                            jsonObject = response.getJSONObject(i);
                            stationModel.setStationId(jsonObject.getString("id"));
                            stationModel.setLocation(jsonObject.getString("location"));
                            stationModel.setStationName(jsonObject.getString("stationName"));
                            list.add(stationModel);
                        }
                        arrayAdapter = new CustomAdapter();
                        listView.setAdapter(arrayAdapter);
                    } catch (JSONException e) {
                        Toast.makeText(JoinQueueSearchList.this,"Data error,please contact administrator: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JoinQueueSearchList.this,"Failed to load the station list - "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

//    custom adapter for the list view
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.custom_station_card,null);

            name = view1.findViewById(R.id.stationName);
            location = view1.findViewById(R.id.location);

            name.setText(list.get(i).getStationName());
            location.setText(list.get(i).getLocation());

            return view1;
        }
    }
}