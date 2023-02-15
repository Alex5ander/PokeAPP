package com.example.pokeapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokeapp.DTO.PokemonResponseDTO;
import com.example.pokeapp.repository.PokemonRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonViewModel extends ViewModel {
    final private PokemonRepository pokemonRepository = new PokemonRepository();
    final private MutableLiveData<PokemonResponseDTO> pokemon = new MutableLiveData<>();
    final private MutableLiveData<String> pokemon_sprite_url = new MutableLiveData<>();
    public LiveData<String> getPokemonSpriteURL() { return this.pokemon_sprite_url; }
    public void togglePokemonSpriteURL() {
        PokemonResponseDTO pokemonValue = pokemon.getValue();
        if(pokemonValue != null) {
            String front_default = pokemonValue.sprites.front_default;
            String front_shiny = pokemonValue.sprites.front_shiny;
            if(this.pokemon_sprite_url.getValue() != null && this.pokemon_sprite_url.getValue().equals( front_default)) {
                this.pokemon_sprite_url.setValue(front_shiny);
            }else{
                this.pokemon_sprite_url.setValue(front_default);
            }
        }
    }
    public LiveData<PokemonResponseDTO> getPokemon() { return this.pokemon; }
    public void setPokemon(PokemonResponseDTO pokemon) { this.pokemon.setValue(pokemon);}
    public void loadPokemonByName(String pokemon_name) {
        if(pokemon.getValue() == null) {
            pokemonRepository.service.getPokemon(pokemon_name).enqueue(new Callback<PokemonResponseDTO>() {
                @Override
                public void onResponse(Call<PokemonResponseDTO> call, Response<PokemonResponseDTO> response) {
                    PokemonResponseDTO pokemon = response.body();
                    if(pokemon != null) {
                        pokemon_sprite_url.setValue(pokemon.sprites.front_default);
                        setPokemon(pokemon);
                    }
                }
                @Override
                public void onFailure(Call<PokemonResponseDTO> call, Throwable t) {}
            });
        }
    }
}
