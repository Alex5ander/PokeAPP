package com.example.pokeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pokeapp.DTO.PokemonResponseDTO;
import com.example.pokeapp.viewmodels.PokemonViewModel;
import com.squareup.picasso.Picasso;

public class Pokemon extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pokemonName = getIntent().getStringExtra("pokemonName");

        setContentView(R.layout.activity_pokemon);
        ProgressBar progressBar = findViewById(R.id.loading_pokemon);
        TextView pokemon_name = findViewById(R.id.pokemon_name);
        ImageView pokemon_sprite = findViewById(R.id.pokemon_sprite);
        TextView pokemon_types = findViewById(R.id.pokemon_types);
        Button button_change_sprite = findViewById(R.id.button_change_sprite);

        PokemonViewModel model = new ViewModelProvider(this).get(PokemonViewModel.class);

        model.loadPokemonByName(pokemonName);
        model.getPokemon().observe(this, pokemon -> {
            if(pokemon != null) {
                String name = Character.toUpperCase(pokemon.name.charAt(0)) + pokemon.name.substring(1);
                pokemon_name.setText(name);
                if(pokemon.types.size() == 2) {
                    pokemon_types.setText(pokemon.types.get(0).type.name+ " / " +pokemon.types.get(1).type.name);
                } else {
                    pokemon_types.setText(pokemon.types.get(0).type.name);
                }
                Picasso.get().load(pokemon.sprites.front_default).into(pokemon_sprite);
                progressBar.setVisibility(ProgressBar.GONE);
            }
        });
        model.getPokemonSpriteURL().observe(this, sprite -> {
            if(sprite != null) {
                Picasso.get().load(sprite).into(pokemon_sprite);
            }
        });
        button_change_sprite.setOnClickListener(v -> { model.togglePokemonSpriteURL(); });
    }
}