package com.example.citibiketracker.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citibiketracker.R;
import com.example.citibiketracker.Station;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    ArrayList<Station> items;

    public MyAdapter(Context context, ArrayList<Station> items) {
        this.context = context;
        this.items = items;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = holder.getLayoutPosition();
        holder.stationId.setText(items.get(pos).getID());
        holder.stationName.setText(items.get(pos).getName());
        Station currentStation = items.get(pos);
        if (currentStation.getFavorite()) {
            holder.ic_favorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            holder.ic_favorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
        holder.ic_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStation.setFavorite(!currentStation.getFavorite());
                ImageView icon = (ImageView) view;
                if (currentStation.getFavorite()) {
                    holder.ic_favorite.setImageResource(android.R.drawable.btn_star_big_on);
                } else {
                    holder.ic_favorite.setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
