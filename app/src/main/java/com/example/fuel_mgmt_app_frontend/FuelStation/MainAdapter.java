package com.example.fuel_mgmt_app_frontend.FuelStation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_mgmt_app_frontend.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Activity activity;
    ArrayList<StationModel> fuelStations;

    public MainAdapter(Activity activity, ArrayList<StationModel> fuelStations){
        this.activity = activity;
        this.fuelStations = fuelStations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fuel_stations,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StationModel model = fuelStations.get(position);

        holder.Name.setText(model.getStationName());

    }

    @Override
    public int getItemCount() {
        return fuelStations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initializer variable
        TextView Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.stationName);
        }
    }
}
