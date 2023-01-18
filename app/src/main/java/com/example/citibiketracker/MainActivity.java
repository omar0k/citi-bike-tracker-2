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

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ApiHelper.OnGetAllStationsTaskCompleted {

    ArrayList<Station> EbikeStations;
    ArrayList<Station> AllStations;
    Button btn_allStations;
    Button btn_ebikeStations;
    Button btn_favoriteStations;
    RecyclerView rv_stationsList;
    public static final String TAG = "TESTINGMain";

    @Override
    public void onTaskCompleted(ArrayList<Station> stations) {
        AllStations = stations;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiHelper apiHelper = new ApiHelper();
        LiveData<ArrayList<Station>> ebikeStationsLiveData = apiHelper.getEbikeStations();
        ebikeStationsLiveData.observe(this, new Observer<ArrayList<Station>>() {
            @Override
            public void onChanged(ArrayList<Station> ebikeStations) {
                // now you can work with the ebikeStations here
                EbikeStations = ebikeStations;
            }
        });
        ApiHelper.GetAllStationsTask task = new ApiHelper.GetAllStationsTask(apiHelper.citiBikeApi, (ApiHelper.OnGetAllStationsTaskCompleted) this);
        task.execute();


        btn_allStations = findViewById(R.id.btn_allStations);
        btn_ebikeStations = findViewById(R.id.btn_ebikeStations);
        btn_favoriteStations = findViewById(R.id.btn_favoriteStations);
        rv_stationsList = findViewById(R.id.rv_stationsList);
        rv_stationsList.setLayoutManager(new LinearLayoutManager(this));
        btn_allStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_stationsList.setAdapter(new MyAdapter(getApplicationContext(), AllStations));
                for (Station s : AllStations) {
                    Log.d("ids", s.getID());
                }
            }
        });
        btn_ebikeStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, String.valueOf(EbikeStations.size()));

            }
        });
        btn_favoriteStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Fav Stations");
            }
        });

    }


}