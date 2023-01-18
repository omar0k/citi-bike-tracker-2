package com.example.citibiketracker.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CitiBikeApi {
    @GET("gbfs/en/station_status.json")
    Call<StationStatus> getStationStatus();

    @GET("gbfs/en/station_information.json")
    Call<StationInformation> getStationInformation();
}