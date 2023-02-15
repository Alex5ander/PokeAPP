package com.example.pokeapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokeapp.DTO.PokemonsByRegionNameResponseDTO;
import com.example.pokeapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokemonsAdapter extends  RecyclerView.Adapter<PokemonsAdapter.ViewHolder> implements Filterable {
    public List<PokemonsByRegionNameResponseDTO.PokemonEntriesDTO> mpokemons = new ArrayList<>();
    private List<PokemonsByRegionNameResponseDTO.PokemonEntriesDTO> pokemons = new ArrayList<>();

    private static OnPokemonClickListener listener;

    public PokemonsAdapter(OnPokemonClickListener listener) {
        this.listener = listener;
    }

    public void setMpokemons(List<PokemonsByRegionNameResponseDTO.PokemonEntriesDTO> mpokemons) {
        this.mpokemons.addAll(mpokemons);
        this.pokemons.addAll(mpokemons);
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
        holder.setPokemon_name(pokemons.get(position).pokemon_species.name);
    }

    @Override
    public int getItemCount() {
        return this.pokemons.size();
    }

    private  Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<PokemonsByRegionNameResponseDTO.PokemonEntriesDTO> filtered = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0) {
                filtered = new ArrayList<>(mpokemons);
            }else {
                filtered = (ArrayList<PokemonsByRegionNameResponseDTO.PokemonEntriesDTO>) mpokemons.stream()
                        .filter(pokemon -> pokemon.pokemon_species.name.toLowerCase().contains(charSequence.toString().toLowerCase().trim()))
                        .collect(Collectors.toList());
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            pokemons.clear();
            pokemons.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView pokemon_name;
        private String name;
        public ViewHolder(View view) {
            super(view);
            pokemon_name = view.findViewById(R.id.region_name);
            view.setOnClickListener(this);
        }

        public void setPokemon_name(String name) {
            String capitalizedName = Character.toUpperCase(name.charAt(0))+name.substring(1);
            pokemon_name.setText(capitalizedName);
            this.name = name;
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(this.name);
        }
    }
    public interface OnPokemonClickListener {
        void onItemClick(String name);
    }
}

