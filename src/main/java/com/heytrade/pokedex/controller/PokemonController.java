package com.heytrade.pokedex.controller;

import com.heytrade.pokedex.domain.Pokemon;
import com.heytrade.pokedex.service.PokemonService;
import com.heytrade.pokedex.service.impl.PokemonServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/pokemon")
public class PokemonController {

    private PokemonServiceImpl pokemonService;

    @Autowired
    public PokemonController(PokemonServiceImpl pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return this.pokemonService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        return this.pokemonService.findPokemonById(id);
    }

    @PostMapping
    public ResponseEntity savePokemon(@Valid @RequestBody Pokemon pokemon) {
        return this.pokemonService.savePokemon(pokemon);
    }

    @PostMapping("/collection")
    public ResponseEntity savePokemon(@Valid @RequestBody List<Pokemon> pokemon) {
        return this.pokemonService.savePokemon(pokemon);
    }

    @PutMapping ("/{id}")
    public ResponseEntity updatePokemon(@PathVariable String id, @Valid @RequestBody Pokemon pokemon) {
        return this.pokemonService.updatePokemon(id, pokemon);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Pokemon> deletePokemonById(@PathVariable String id) {
        return this.pokemonService.deletePokemonById(id);
    }
}
