package com.example.citibiketracker.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class StationStatus {

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("stations")
        private ArrayList<stationForStatus> stations;
        public ArrayList<stationForStatus> getStations() {
            return stations;
        }
    }

    public static class stationForStatus {
        @SerializedName("last_reported")
        private int lastReported;
        @SerializedName("num_bikes_disabled")
        private int numBikesDisabled;
        @SerializedName("num_docks_available")
        private int numDocksAvailable;
        @SerializedName("is_returning")
        private int isReturning;
        @SerializedName("num_ebikes_available")
        private int numEbikesAvailable;
        @SerializedName("legacy_id")
        private String legacyId;
        @SerializedName("station_id")
        private String stationId;
        @SerializedName("station_status")
        private String stationStatus;
        @SerializedName("num_bikes_available")
        private int numBikesAvailable;
        public int getLastReported() {
            return lastReported;
        }

        public int getNumBikesDisabled() {
            return numBikesDisabled;
        }

        public int getNumDocksAvailable() {
            return numDocksAvailable;
        }

        public int getIsReturning() {
            return isReturning;
        }

        public int getNumEbikesAvailable() {
            return numEbikesAvailable;
        }

        public String getLegacyId() {
            return legacyId;
        }

        public String getStationId() {
            return stationId;
        }

        public String getStationStatus() {
            return stationStatus;
        }

        public int getNumBikesAvailable() {
            return numBikesAvailable;
        }
    }
}