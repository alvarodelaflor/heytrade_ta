package com.heytrade.pokedex.service;

import com.google.gson.Gson;
import com.heytrade.pokedex.domain.Pokemon;
import com.heytrade.pokedex.repository.PokemonRepository;
import com.heytrade.pokedex.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private EvolutionService evolutionService;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    private Pokemon pokemon01;
    private Pokemon pokemon02;
    private List<Pokemon> pokemons;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        String pokemonJson01 = "{\"id\":\"002\",\"name\":\"Ivysaur\",\"classification\":\"SeedPokémon\",\"types\":[\"Grass\",\"Poison\"],\"resistant\":[\"Water\",\"Electric\",\"Grass\",\"Fighting\",\"Fairy\"],\"weaknesses\":[\"Fire\",\"Ice\",\"Flying\",\"Psychic\"],\"weight\":{\"minimum\":\"11.38kg\",\"maximum\":\"14.63kg\"},\"height\":{\"minimum\":\"0.88m\",\"maximum\":\"1.13m\"},\"fleeRate\":0.07,\"previousEvolutions\":[{\"id\":1,\"name\":\"Bulbasaur\"}],\"evolutionRequirements\":{\"amount\":100,\"name\":\"Bulbasaurcandies\"},\"evolutions\":[{\"id\":3,\"name\":\"Venusaur\"}],\"maxCP\":1483,\"maxHP\":1632,\"attacks\":{\"fast\":[{\"name\":\"RazorLeaf\",\"type\":\"Grass\",\"damage\":15},{\"name\":\"VineWhip\",\"type\":\"Grass\",\"damage\":7}],\"special\":[{\"name\":\"PowerWhip\",\"type\":\"Grass\",\"damage\":70},{\"name\":\"SludgeBomb\",\"type\":\"Poison\",\"damage\":55},{\"name\":\"SolarBeam\",\"type\":\"Grass\",\"damage\":120}]}}";
        pokemon01 = new Gson().fromJson(pokemonJson01, Pokemon.class);

        String pokemonJson02 = "{\"id\":\"003\",\"name\":\"Ivysaur1\",\"classification\":\"SeedPokémon\",\"types\":[\"Grass\",\"Poison\"],\"resistant\":[\"Water\",\"Electric\",\"Grass\",\"Fighting\",\"Fairy\"],\"weaknesses\":[\"Fire\",\"Ice\",\"Flying\",\"Psychic\"],\"weight\":{\"minimum\":\"11.38kg\",\"maximum\":\"14.63kg\"},\"height\":{\"minimum\":\"0.88m\",\"maximum\":\"1.13m\"},\"fleeRate\":0.07,\"previousEvolutions\":[{\"id\":1,\"name\":\"Bulbasaur\"}],\"evolutionRequirements\":{\"amount\":100,\"name\":\"Bulbasaurcandies\"},\"evolutions\":[{\"id\":3,\"name\":\"Venusaur\"}],\"maxCP\":1483,\"maxHP\":1632,\"attacks\":{\"fast\":[{\"name\":\"RazorLeaf\",\"type\":\"Grass\",\"damage\":15},{\"name\":\"VineWhip\",\"type\":\"Grass\",\"damage\":7}],\"special\":[{\"name\":\"PowerWhip\",\"type\":\"Grass\",\"damage\":70},{\"name\":\"SludgeBomb\",\"type\":\"Poison\",\"damage\":55},{\"name\":\"SolarBeam\",\"type\":\"Grass\",\"damage\":120}]}}";
        pokemon02 = new Gson().fromJson(pokemonJson02, Pokemon.class);

        pokemons = Arrays.asList(pokemon01, pokemon02);
    }

    @Test
    void findAll() {
        when(pokemonRepository.findAll()).thenReturn(Arrays.asList(pokemon01));
        assertNotNull(pokemonService.findAll());
    }

    @Test
    void findById404() {
        Optional<Pokemon> optional = Optional.empty();
        String id = "001";

        when(pokemonRepository.findById(id)).thenReturn(optional);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        assertEquals(this.pokemonService.findPokemonById(id), responseEntity);
    }

    @Test
    void findById200() {
        Optional<Pokemon> optional = Optional.of(pokemon01);
        String id = "001";

        when(pokemonRepository.findById(id)).thenReturn(optional);
        ResponseEntity responseEntity = new ResponseEntity(optional.get(), HttpStatus.OK);
        assertEquals(this.pokemonService.findPokemonById(id), responseEntity);
    }

    @Test
    void savePokemonSingle() {
        when(this.pokemonRepository.save(pokemon01)).thenReturn(pokemon01);
        pokemon01.getEvolutions().stream().forEach(x -> when(this.evolutionService.findEvolutionById(x.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        if (pokemon01.getPreviousEvolutions() != null) {
            pokemon01.getPreviousEvolutions().stream().forEach(x -> when(this.evolutionService.findEvolutionById(x.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        }

        ResponseEntity responseEntity = new ResponseEntity(pokemon01, HttpStatus.OK);

        assertEquals(this.pokemonService.savePokemon(pokemon01), responseEntity);
    }

    @Test
    void savePokemonCollection() {

        when(this.pokemonRepository.saveAll(Arrays.asList(pokemon01, pokemon02))).thenReturn(pokemons);

        pokemon01.getEvolutions().stream().forEach(x -> when(this.evolutionService.findEvolutionById(x.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        if (pokemon01.getPreviousEvolutions() != null) {
            pokemon01.getPreviousEvolutions().stream().forEach(x -> when(this.evolutionService.findEvolutionById(x.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        }

        pokemon02.getEvolutions().stream().forEach(x -> when(this.evolutionService.findEvolutionById(x.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        if (pokemon02.getPreviousEvolutions() != null) {
            pokemon02.getPreviousEvolutions().stream().forEach(x -> when(this.evolutionService.findEvolutionById(x.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        }

        ResponseEntity responseEntity = new ResponseEntity(pokemons, HttpStatus.OK);

        assertEquals(this.pokemonService.savePokemon(pokemons), responseEntity);
    }

    @Test
    void updatePokemon200() {
        String id = "001";

        when(this.pokemonRepository.findById(id)).thenReturn(Optional.of(pokemon01));
        when(this.pokemonRepository.save(pokemon01)).thenReturn(pokemon01);

        pokemon01.getEvolutions().stream().forEach(x -> when(this.evolutionService.findEvolutionById(x.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        if (pokemon01.getPreviousEvolutions() != null) {
            pokemon01.getPreviousEvolutions().stream().forEach(x -> when(this.evolutionService.findEvolutionById(x.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        }

        ResponseEntity responseEntity = new ResponseEntity(pokemon01, HttpStatus.OK);

        assertEquals(this.pokemonService.updatePokemon(id, pokemon01), responseEntity);
    }

    @Test
    void updatePokemon404() {
        String id = "001";

        when(this.pokemonRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        assertEquals(this.pokemonService.updatePokemon(id, pokemon01), responseEntity);
    }

    @Test
    void deletePokemon200() {
        String id = "001";

        when(pokemonRepository.findById(id)).thenReturn(Optional.of(pokemon01));

        ResponseEntity responseEntity = new ResponseEntity(pokemon01, HttpStatus.OK);

        assertEquals(this.pokemonService.deletePokemonById(id), responseEntity);
    }

    @Test
    void deletePokemon404() {
        String id = "001";

        when(pokemonRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        assertEquals(this.pokemonService.deletePokemonById(id), responseEntity);
    }

    @Test
    void deletePokemon500() {
        String id = "001";

        when(pokemonRepository.findById(id)).thenThrow(NullPointerException.class);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(this.pokemonService.deletePokemonById(id), responseEntity);
    }
}
