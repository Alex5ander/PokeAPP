package com.example.pokeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeapp.DTO.RegionsResponseDTO;
import com.example.pokeapp.R;

import java.util.ArrayList;
import java.util.List;

public class RegionsAdapter extends RecyclerView.Adapter<RegionsAdapter.ViewHolder> {
    private List<RegionsResponseDTO.Region> regions = new ArrayList<>();
    static private OnRegionClickListener listener;

    public RegionsAdapter(OnRegionClickListener listener) {
        this.listener = listener;
    }

    public void setRegions(List<RegionsResponseDTO.Region> regions) {
        this.regions = regions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setRegion_name(regions.get(position).name);
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView region_name;
        String name;
        public ViewHolder(View view) {
         super(view);
         region_name = view.findViewById(R.id.region_name);
         view.setOnClickListener(this);
        }

        public void setRegion_name(String name) {
            String capitalizedName = Character.toUpperCase(name.charAt(0))+name.substring(1);
            region_name.setText(capitalizedName);
            this.name = name;
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(this.name);
        }
    }
    public interface OnRegionClickListener {
        void onItemClick(String region_name);
    }

}