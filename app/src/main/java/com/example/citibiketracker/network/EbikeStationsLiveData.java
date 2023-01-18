package com.example.citibiketracker.network;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.citibiketracker.Station;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EbikeStationsLiveData extends LiveData<ArrayList<Station>> {
    private final CitiBikeApi citiBikeApi;

    public EbikeStationsLiveData(CitiBikeApi citiBikeApi) {
        this.citiBikeApi = citiBikeApi;
    }
    @Override
    protected void onActive() {
        super.onActive();
        citiBikeApi.getStationStatus().enqueue(new Callback<StationStatus>() {
            @Override
            public void onResponse(Call<StationStatus> call, Response<StationStatus> response) {
                if (response.isSuccessful()) {
                    StationStatus stationStatus = response.body();
                    ArrayList<Station> ebikeStations = new ArrayList<>();
                    assert stationStatus != null;
                    for (StationStatus.stationForStatus station : stationStatus.getData().getStations()) {
                        if (station.getNumEbikesAvailable() == station.getNumBikesAvailable()) {
                            ebikeStations.add(new Station("ebikestation", station.getStationId()));
                        }
                    }
                    setValue(ebikeStations);
                }
            }
            @Override
            public void onFailure(Call<StationStatus> call, Throwable t) {
                Log.e("ERROR", t.toString(), t);
            }
        });
    }
    //...
}
