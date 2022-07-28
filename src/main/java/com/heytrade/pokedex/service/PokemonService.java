package com.heytrade.pokedex.service;

import com.heytrade.pokedex.domain.Pokemon;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PokemonService {
    @Cacheable("pokemons")
    ResponseEntity<List<Pokemon>> findAll();

    ResponseEntity<Pokemon> findPokemonById(String id);

    ResponseEntity savePokemon(Pokemon evolution);

    ResponseEntity savePokemon(List<Pokemon> pokemons);

    ResponseEntity updatePokemon(String id, Pokemon evolution);

    ResponseEntity<Pokemon> deletePokemonById(String id);
}
