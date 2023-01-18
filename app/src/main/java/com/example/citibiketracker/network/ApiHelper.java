package com.example.citibiketracker.network;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.citibiketracker.Station;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHelper {
    public static final String TAG = "Api Helper";
    public final CitiBikeApi citiBikeApi;
    public ArrayList<Station> listOfAllStations = new ArrayList<>();
    public ArrayList<Station> listOfEbikeStations = new ArrayList<>();
    private ArrayList<StationStatus.stationForStatus> listOfStationStatuses = new ArrayList<>();
    private ArrayList<StationInformation.stationForInfo> listOfStationInfo = new ArrayList<>();

    public ApiHelper() {
        citiBikeApi = NetworkService.getInstance().getRetrofit().create(CitiBikeApi.class);
    }

    public void getStationStatus() {
        Call<StationStatus> call = citiBikeApi.getStationStatus();
        call.enqueue(new Callback<StationStatus>() {
            @Override
            public void onResponse(@NonNull Call<StationStatus> call, @NonNull Response<StationStatus> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        StationStatus stationStatus = response.body();
                        ArrayList<StationStatus.stationForStatus> stations = stationStatus.getData().getStations();
                        setListOfStationStatuses(stations);
                        for (StationStatus.stationForStatus station : stations) {
                            if (station.getNumEbikesAvailable() == station.getNumBikesAvailable()) {
                                listOfEbikeStations.add(new Station("ebikestation", station.getStationId()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<StationStatus> call, @NonNull Throwable t) {
                Log.e("ERROR", t.toString(), t);
            }
        });
    }

    public EbikeStationsLiveData getEbikeStations() {
        return new EbikeStationsLiveData(citiBikeApi);
    }

    public void getStationInformation() {
        Call<StationInformation> call = citiBikeApi.getStationInformation();
        call.enqueue(new Callback<StationInformation>() {
            @Override
            public void onResponse(@NonNull Call<StationInformation> call, @NonNull Response<StationInformation> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        StationInformation stationInformation = response.body();
                        ArrayList<StationInformation.stationForInfo> stations = stationInformation.getData().getStations();
                        setListOfStationInfo(stations);
                        for (StationInformation.stationForInfo stationInfo : stations) {
                            for (Station ebikeStation : getListOfEbikeStations()) {
                                if (ebikeStation.getID().equals(stationInfo.getStationId())) {
                                    ebikeStation.setName(stationInfo.getName());
                                }
                            }
                            for (StationStatus.stationForStatus stationStatus : getListOfStationStatuses()) {
                                if (stationInfo.getStationId().equals(stationStatus.getStationId())) {
                                    listOfAllStations.add(new Station(stationInfo.getName(), stationInfo.getStationId()));
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<StationInformation> call, @NonNull Throwable t) {
                Log.e("ERROR", t.toString(), t);
            }
        });

    }

    public static class GetAllStationsTask extends AsyncTask<Void, Void, ArrayList<Station>> {

        private final CitiBikeApi citiBikeApi;

        public GetAllStationsTask(CitiBikeApi citiBikeApi) {
            this.citiBikeApi = citiBikeApi;
        }
        private OnGetAllStationsTaskCompleted listener;

        public GetAllStationsTask(CitiBikeApi citiBikeApi, OnGetAllStationsTaskCompleted listener) {
            this.citiBikeApi = citiBikeApi;
            this.listener = listener;
        }


        @Override

        protected ArrayList<Station> doInBackground(Void... voids) {
            try {
                Response<StationInformation> response = citiBikeApi.getStationInformation().execute();
                if (response.isSuccessful()) {
                    StationInformation stationInformation = response.body();
                    ArrayList<Station> allStations = new ArrayList<>();
                    for (StationInformation.stationForInfo stationInfo : stationInformation.getData().getStations()) {
                        allStations.add(new Station(stationInfo.getName(), stationInfo.getStationId()));
                    }
                    return allStations;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Station> allStations) {
            ApiHelper apiHelper = new ApiHelper();
            super.onPostExecute(allStations);
            apiHelper.setListOfAllStations(allStations);
            listener.onTaskCompleted(allStations);
        }

    }
    public interface OnGetAllStationsTaskCompleted {
        void onTaskCompleted(ArrayList<Station> stations);
    }

    public void updateStationNames() {
        getStationStatus();
        getStationInformation();
    }

    public ArrayList<Station> getListOfAllStations() {
        return listOfAllStations;
    }

    public void setListOfAllStations(ArrayList<Station> listOfAllStations) {
        this.listOfAllStations = listOfAllStations;
    }

    public ArrayList<Station> getListOfEbikeStations() {
        Log.d("testtt", String.valueOf(listOfEbikeStations.size()));
        return listOfEbikeStations;
    }

    public void setListOfEbikeStations(ArrayList<Station> listOfEbikeStations) {
        this.listOfEbikeStations = listOfEbikeStations;
    }

    public ArrayList<StationStatus.stationForStatus> getListOfStationStatuses() {
        return listOfStationStatuses;
    }

    public void setListOfStationStatuses
            (ArrayList<StationStatus.stationForStatus> listOfStationStatuses) {
        this.listOfStationStatuses = listOfStationStatuses;
    }

    public ArrayList<StationInformation.stationForInfo> getListOfStationInfo() {
        return listOfStationInfo;
    }

    public void setListOfStationInfo
            (ArrayList<StationInformation.stationForInfo> listOfStationInfo) {
        this.listOfStationInfo = listOfStationInfo;
    }
}
