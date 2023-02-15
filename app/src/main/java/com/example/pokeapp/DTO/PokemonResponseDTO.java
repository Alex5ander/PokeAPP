package com.example.pokeapp.DTO;

import java.util.List;

public class PokemonResponseDTO {
    public String name;
    public Sprites sprites;
    public class Sprites {
        public String front_default;
        public String front_shiny;
    }

    public List<Type> types;

    public class Type {
        public TypeDTO type;
    }

    public class TypeDTO {
        public String name;
    }
}
