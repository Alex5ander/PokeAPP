package com.example.pokeapp.repository;

import com.example.pokeapp.remote.PokemonDAO;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonRepository {
    public PokemonDAO service;
    public PokemonRepository() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(PokemonDAO.class);
    }
}
