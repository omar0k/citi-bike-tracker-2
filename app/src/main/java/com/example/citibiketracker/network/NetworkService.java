package com.example.citibiketracker.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private final Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder().baseUrl("https://gbfs.citibikenyc.com/")
                .addConverterFactory(GsonConverterFactory
                .create())
                .build();
    }
    public static NetworkService getInstance(){
        if (mInstance==null)
        {
            mInstance=new NetworkService();
        }
        return mInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
