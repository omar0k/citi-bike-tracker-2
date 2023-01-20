package com.example.citibiketracker;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.citibiketracker.RecyclerView.MyAdapter;
import com.example.citibiketracker.network.ApiHelper;
import com.example.citibiketracker.network.StationInformation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements ApiHelper.OnGetAllStationsTaskCompleted {

    private ArrayList<Station> EbikeStations;
    private ArrayList<Station> AllStations;
    private ArrayList<Station> FavoriteStations;
    private RecyclerView rv_stationsList;
    public static final String TAG = "TESTINGMain";

    private void getFavoriteStations(ArrayList<Station> allStations, ArrayList<Station> ebikeStations) {
        Set<Station> favStations = new HashSet<>();
        for (Station s : allStations) {
            if (s.getFavorite()) {
                favStations.add(s);
            }
        }
        for (Station s2 : ebikeStations) {
            if (s2.getFavorite()) {
                FavoriteStations.add(s2);
            }
        }
        FavoriteStations=new ArrayList<>(favStations);
    }


    @Override
    public void onTaskCompleted(ArrayList<Station> stations) {
        AllStations = stations;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiHelper apiHelper = new ApiHelper();
        LiveData<ArrayList<Station>> ebikeStationsLiveData = apiHelper.getEbikeStations();
        ApiHelper.GetAllStationsTask task = new ApiHelper.GetAllStationsTask(apiHelper.citiBikeApi, (ApiHelper.OnGetAllStationsTaskCompleted) MainActivity.this);
        task.execute();
        ebikeStationsLiveData.observe(this, new Observer<ArrayList<Station>>() {
            @Override
            public void onChanged(ArrayList<Station> ebikeStations) {
                EbikeStations = ebikeStations;
            }
        });
        FavoriteStations = new ArrayList<Station>();
        Button btn_allStations = findViewById(R.id.btn_allStations);
        Button btn_ebikeStations = findViewById(R.id.btn_ebikeStations);
        Button btn_favoriteStations = findViewById(R.id.btn_favoriteStations);
        rv_stationsList = findViewById(R.id.rv_stationsList);
        rv_stationsList.setLayoutManager(new LinearLayoutManager(this));
        btn_allStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Station s : EbikeStations) {
                    for (Station stationForInfo : AllStations) {
                        if (s.getID().equals(stationForInfo.getID())) {
                            s.setName(stationForInfo.getName());
                            if (s.getFavorite()) {
                                stationForInfo.setFavorite(true);
                            }
                        }
                    }
                }
                rv_stationsList.setAdapter(new MyAdapter(getApplicationContext(), AllStations));
            }
        });
        btn_ebikeStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Station s : EbikeStations) {
                    for (Station stationForInfo : AllStations) {
                        if (s.getID().equals(stationForInfo.getID())) {
                            s.setName(stationForInfo.getName());
                            if (stationForInfo.getFavorite()) {
                                s.setFavorite(true);
                            }
                        }
                    }
                }
                rv_stationsList.setAdapter(new MyAdapter(getApplicationContext(), EbikeStations));
            }
        });
        btn_favoriteStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFavoriteStations(AllStations, EbikeStations);
                rv_stationsList.setAdapter(new MyAdapter(getApplicationContext(), FavoriteStations));
            }
        });
    }
}