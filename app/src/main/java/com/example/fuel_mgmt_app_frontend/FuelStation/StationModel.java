package com.example.fuel_mgmt_app_frontend.FuelStation;

import java.io.Serializable;
import java.util.HashMap;

public class StationModel implements Serializable {

    String stationId,stationName,location,arrivalTime,finishTime,email;
    HashMap<String,Boolean> availabilities;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HashMap<String, Boolean> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(HashMap<String,Boolean> availabilities) {
        this.availabilities = availabilities;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

}
