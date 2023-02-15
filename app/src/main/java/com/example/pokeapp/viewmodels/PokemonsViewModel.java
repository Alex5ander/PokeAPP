package com.example.pokeapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokeapp.DTO.PokemonsByRegionNameResponseDTO;
import com.example.pokeapp.repository.PokemonRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonsViewModel extends ViewModel {
    PokemonRepository pokemonRepository = new PokemonRepository();
    private MutableLiveData<List<PokemonsByRegionNameResponseDTO.PokemonEntriesDTO>> pokemons = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<PokemonsByRegionNameResponseDTO.PokemonEntriesDTO>> getPokemons() {return this.pokemons;}

    public void setPokemons(List<PokemonsByRegionNameResponseDTO.PokemonEntriesDTO> pokemons) {
        if(pokemons.size() > 0) {
            this.pokemons.setValue(pokemons);
        }
    }

    public void loadPokemonsByRegionName(String region_name) {
        if(pokemons.getValue().size() == 0) {
            pokemonRepository.service.getPokemonsByRegionName(region_name).enqueue(new Callback<PokemonsByRegionNameResponseDTO>() {
                @Override
                public void onResponse(Call<PokemonsByRegionNameResponseDTO> call, Response<PokemonsByRegionNameResponseDTO> response) {
                    PokemonsByRegionNameResponseDTO pokemons = response.body();
                    setPokemons(pokemons.pokemon_entries);
                }

                @Override
                public void onFailure(Call<PokemonsByRegionNameResponseDTO> call, Throwable t) {

                }
            });
        }
    }
}
