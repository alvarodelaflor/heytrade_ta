package com.heytrade.pokedex.service.impl;

import com.heytrade.pokedex.domain.Evolution;
import com.heytrade.pokedex.domain.Pokemon;
import com.heytrade.pokedex.repository.PokemonRepository;
import com.heytrade.pokedex.service.EvolutionService;
import com.heytrade.pokedex.service.PokemonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PokemonServiceImpl implements PokemonService {
    PokemonRepository pokemonRepository;
    EvolutionService evolutionService;

    @Override
    @Cacheable("pokemons")
    public ResponseEntity<List<Pokemon>> findAll() {
        return new ResponseEntity<>(this.pokemonRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Cacheable("pokemons")
    public ResponseEntity<Pokemon> findPokemonById(String id) {
        Optional<Pokemon> pokemon = this.pokemonRepository.findById(id);
        return pokemon.isPresent() ? new ResponseEntity<>(pokemon.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity savePokemon(Pokemon pokemon) {
        Pokemon pokemonSaved = this.pokemonRepository.save(pokemon);
        this.checkValidEvolution(pokemonSaved);
        return new ResponseEntity(pokemonSaved, HttpStatus.OK);
    }

    @Override
    public ResponseEntity savePokemon(List<Pokemon> pokemons) {
        List<Pokemon> pokemonSaved = this.pokemonRepository.saveAll(pokemons);
        this.checkValidEvolution(pokemonSaved);
        return new ResponseEntity(pokemonSaved, HttpStatus.OK);
    }

    private void checkValidEvolution(Pokemon pokemonSaved) {
        List<Evolution> evolutions = new ArrayList<>();

        if (pokemonSaved.getEvolutions() != null) {
            evolutions.addAll(pokemonSaved.getEvolutions());
        }
        if (pokemonSaved.getPreviousEvolutions() != null) {
            evolutions.addAll(pokemonSaved.getPreviousEvolutions());
        }

        evolutions.stream().forEach(x -> {
            Evolution evolutionFound = this.evolutionService.findEvolutionById(x.getId()).getBody();
            if (evolutionFound == null) {
                this.evolutionService.saveEvolution(x);
            }
        });
    }

    private void checkValidEvolution(List<Pokemon> pokemonsSaved) {
        List<Evolution> evolutions = new ArrayList<>();
        evolutions.addAll(pokemonsSaved.stream().filter(x -> x.getEvolutions() != null).flatMap(x -> x.getEvolutions().stream()).collect(Collectors.toList()));
        evolutions.addAll(pokemonsSaved.stream().filter(x -> x.getPreviousEvolutions() != null).flatMap(x -> x.getPreviousEvolutions().stream()).collect(Collectors.toList()));

        evolutions.stream().forEach(x -> {
            Evolution evolutionFound = this.evolutionService.findEvolutionById(x.getId()).getBody();
            if (evolutionFound == null) {
                this.evolutionService.saveEvolution(x);
            }
        });
    }

    @Override
    public ResponseEntity updatePokemon(String id, Pokemon pokemon) {
        ResponseEntity res;
        Pokemon pokemonSearch = this.findPokemonById(id).getBody();
        if (pokemonSearch != null) {
            pokemon.setId(id);
            Pokemon pokemonSaved = this.pokemonRepository.save(pokemon);
            res = new ResponseEntity(pokemonSaved, HttpStatus.OK);
        } else {
            res = new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return res;
    }

    @Override
    public ResponseEntity<Pokemon> deletePokemonById(String id) {
        ResponseEntity<Pokemon> res;
        try {
            Pokemon pokemon = this.findPokemonById(id).getBody();
            if (pokemon != null) {
                this.pokemonRepository.delete((Pokemon) pokemon);
                res = new ResponseEntity<>((Pokemon) pokemon, HttpStatus.OK);
            } else {
                res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            res = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }
}
