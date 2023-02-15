package com.example.pokeapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokeapp.DTO.PokemonResponseDTO;
import com.example.pokeapp.DTO.PokemonsByRegionNameResponseDTO;
import com.example.pokeapp.DTO.RegionsResponseDTO;
import com.example.pokeapp.repository.PokemonRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    PokemonRepository pokemonRepository = new PokemonRepository();
    private MutableLiveData<List<RegionsResponseDTO.Region>> regions = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<RegionsResponseDTO.Region>> getRegions() {
        return this.regions;
    }
    public void setRegions(List<RegionsResponseDTO.Region> regions) {
       if(regions.size() > 0) {
           this.regions.setValue(regions);
       }
    }
    public void loadRegions() {
        pokemonRepository.service.getRegions().enqueue(new Callback<RegionsResponseDTO>() {
            @Override
            public void onResponse(Call<RegionsResponseDTO> call, Response<RegionsResponseDTO> response) {
                RegionsResponseDTO regions = response.body();
                setRegions(regions.results);
            }

            @Override
            public void onFailure(Call<RegionsResponseDTO> call, Throwable t) {}
        });
    }
}
