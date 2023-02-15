package com.example.pokeapp.remote;

import com.example.pokeapp.DTO.PokemonResponseDTO;
import com.example.pokeapp.DTO.PokemonsByRegionNameResponseDTO;
import com.example.pokeapp.DTO.RegionsResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonDAO {
    @GET("pokedex")
    Call<RegionsResponseDTO> getRegions();

    @GET("pokedex/{region}")
    Call<PokemonsByRegionNameResponseDTO> getPokemonsByRegionName(@Path("region") String region);

    @GET("pokemon/{name}")
    Call<PokemonResponseDTO> getPokemon(@Path("name") String name);
}
