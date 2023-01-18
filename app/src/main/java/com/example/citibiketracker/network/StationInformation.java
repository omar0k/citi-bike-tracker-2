package com.example.citibiketracker.network;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StationInformation {

    @SerializedName("data")
    private infoData data;

    public infoData getData() {
        return data;
    }
    public static class infoData{
        @SerializedName("stations")
        private ArrayList<stationForInfo> stations;
        public ArrayList<stationForInfo> getStations(){return stations;}
    }
    public static class stationForInfo {
        @SerializedName("station_id")
        private String stationId;
        @SerializedName("name")
        private String name;

        public String getStationId() {
            return stationId;
        }

        public void setStationId(String stationId) {
            this.stationId = stationId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
