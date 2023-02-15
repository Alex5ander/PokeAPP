package com.example.pokeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.pokeapp.adapter.PokemonsAdapter;
import com.example.pokeapp.viewmodels.PokemonsViewModel;

public class Pokemons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String region_name = getIntent().getStringExtra("region_name");
        PokemonsViewModel model = new ViewModelProvider(this).get(PokemonsViewModel.class);
        model.loadPokemonsByRegionName(region_name);

        PokemonsAdapter adapter = new PokemonsAdapter(name -> {
            Intent intent = new Intent(this, Pokemon.class);
            intent.putExtra("pokemonName", name);
            startActivity(intent);
        });

        setContentView(R.layout.activity_pokemons);
        TextView regionName = findViewById(R.id.region_title);
        RecyclerView recyclerView = findViewById(R.id.pokemons_list);

        regionName.setText("Região "+region_name);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(divider);

        ProgressBar progressBar = findViewById(R.id.loading_pokemons);
        SearchView search_pokemons = findViewById(R.id.search_pokemon);

        search_pokemons.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Handler handler = new Handler();
                handler.postDelayed( () -> {adapter.getFilter().filter(s);}, 1000);
                return false;
            }
        });

        model.getPokemons().observe(this, data -> {
            adapter.setMpokemons(data);
            if(data.size() > 0) { progressBar.setVisibility(ProgressBar.GONE);}
        });
    }
}