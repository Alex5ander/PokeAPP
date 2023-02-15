package com.example.pokeapp.DTO;

import java.util.List;

public class PokemonsByRegionNameResponseDTO {
    public List<PokemonEntriesDTO> pokemon_entries;

    public class PokemonEntriesDTO {
        public PokemonSpeciesDTO pokemon_species;

        public class PokemonSpeciesDTO {
            public String name;
        }
    }
}
