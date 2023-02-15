package com.example.pokeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.pokeapp.viewmodels.MainViewModel;
import com.example.pokeapp.adapter.RegionsAdapter;
public class MainActivity extends AppCompatActivity {
    boolean isInitialLaunch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);
        RegionsAdapter adapter = new RegionsAdapter(name -> {
            Intent pokemons = new Intent(this, Pokemons.class);
            pokemons.putExtra("region_name", name);
            startActivity(pokemons);
        });

        setContentView(R.layout.activity_main);

        ProgressBar progressBar = findViewById(R.id.loading_regions);
        RecyclerView recylerView = findViewById(R.id.regions_list);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recylerView.setLayoutManager(layout);
        recylerView.setAdapter(adapter);

        addDivider(recylerView);
        model.loadRegions();

        model.getRegions().observe(this, data -> {
            adapter.setRegions(data);
            if(data.size() > 0) { progressBar.setVisibility(ProgressBar.GONE); }
        });
    }

    public void addDivider(RecyclerView recyclerView) {
        DividerItemDecoration divider = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        divider.setDrawable(getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(divider);
    }
}