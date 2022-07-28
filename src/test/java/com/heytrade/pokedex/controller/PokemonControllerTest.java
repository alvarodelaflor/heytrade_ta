package com.heytrade.pokedex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.heytrade.pokedex.domain.Pokemon;
import com.heytrade.pokedex.service.EvolutionService;
import com.heytrade.pokedex.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.util.BsonUtils.toJson;

@SpringBootTest
@AutoConfigureMockMvc
public class PokemonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PokemonServiceImpl pokemonService;

    private Pokemon pokemon01;
    private Pokemon pokemon02;
    private List<Pokemon> pokemons;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        String pokemonJson01 = "{\"id\":\"002\",\"name\":\"Ivysaur\",\"classification\":\"SeedPokémon\",\"types\":[\"Grass\",\"Poison\"],\"resistant\":[\"Water\",\"Electric\",\"Grass\",\"Fighting\",\"Fairy\"],\"weaknesses\":[\"Fire\",\"Ice\",\"Flying\",\"Psychic\"],\"weight\":{\"minimum\":\"11.38kg\",\"maximum\":\"14.63kg\"},\"height\":{\"minimum\":\"0.88m\",\"maximum\":\"1.13m\"},\"fleeRate\":0.07,\"Previous evolution(s)\":[{\"id\":1,\"name\":\"Bulbasaur\"}],\"evolutionRequirements\":{\"amount\":100,\"name\":\"Bulbasaurcandies\"},\"evolutions\":[{\"id\":3,\"name\":\"Venusaur\"}],\"maxCP\":1483,\"maxHP\":1632,\"attacks\":{\"fast\":[{\"name\":\"RazorLeaf\",\"type\":\"Grass\",\"damage\":15},{\"name\":\"VineWhip\",\"type\":\"Grass\",\"damage\":7}],\"special\":[{\"name\":\"PowerWhip\",\"type\":\"Grass\",\"damage\":70},{\"name\":\"SludgeBomb\",\"type\":\"Poison\",\"damage\":55},{\"name\":\"SolarBeam\",\"type\":\"Grass\",\"damage\":120}]}}";
        pokemon01 = new Gson().fromJson(pokemonJson01, Pokemon.class);

        String pokemonJson02 = "{\"id\":\"003\",\"name\":\"Ivysaur1\",\"classification\":\"SeedPokémon\",\"types\":[\"Grass\",\"Poison\"],\"resistant\":[\"Water\",\"Electric\",\"Grass\",\"Fighting\",\"Fairy\"],\"weaknesses\":[\"Fire\",\"Ice\",\"Flying\",\"Psychic\"],\"weight\":{\"minimum\":\"11.38kg\",\"maximum\":\"14.63kg\"},\"height\":{\"minimum\":\"0.88m\",\"maximum\":\"1.13m\"},\"fleeRate\":0.07,\"Previous evolution(s)\":[{\"id\":1,\"name\":\"Bulbasaur\"}],\"evolutionRequirements\":{\"amount\":100,\"name\":\"Bulbasaurcandies\"},\"evolutions\":[{\"id\":3,\"name\":\"Venusaur\"}],\"maxCP\":1483,\"maxHP\":1632,\"attacks\":{\"fast\":[{\"name\":\"RazorLeaf\",\"type\":\"Grass\",\"damage\":15},{\"name\":\"VineWhip\",\"type\":\"Grass\",\"damage\":7}],\"special\":[{\"name\":\"PowerWhip\",\"type\":\"Grass\",\"damage\":70},{\"name\":\"SludgeBomb\",\"type\":\"Poison\",\"damage\":55},{\"name\":\"SolarBeam\",\"type\":\"Grass\",\"damage\":120}]}}";
        pokemon02 = new Gson().fromJson(pokemonJson02, Pokemon.class);

        pokemons = Arrays.asList(pokemon01, pokemon02);
    }

    @Test
    void getAll() throws Exception {
        ResponseEntity response = new ResponseEntity(pokemons, HttpStatus.OK);
        when(pokemonService.findAll()).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/pokemon");
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        List<Pokemon> pokemonsResult = Arrays.asList(new Gson().fromJson(result, Pokemon[].class));

        assertEquals(pokemonsResult, pokemons);
    }

    @Test
    void getById() throws Exception {
        String id = "001";

        ResponseEntity response = new ResponseEntity(pokemon01, HttpStatus.OK);
        when(pokemonService.findPokemonById(id)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/pokemon/" + id);
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Pokemon pokemonResult = new Gson().fromJson(result, Pokemon.class);

        assertEquals(pokemonResult, pokemon01);
    }

    @Test
    void savePokemon() throws Exception {
        ResponseEntity response = new ResponseEntity(pokemon01, HttpStatus.OK);
        when(pokemonService.savePokemon(pokemon01)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/pokemon").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(pokemon01));
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Pokemon pokemonResult = new Gson().fromJson(result, Pokemon.class);

        assertEquals(pokemonResult, pokemon01);
    }

    @Test
    void savePokemons() throws Exception {
        ResponseEntity response = new ResponseEntity(pokemons, HttpStatus.OK);
        when(pokemonService.savePokemon(pokemons)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/pokemon/collection").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(pokemons));
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        List<Pokemon> pokemonResult = Arrays.asList(new Gson().fromJson(result, Pokemon[].class));

        assertEquals(pokemonResult, pokemons);
    }

    @Test
    void updatePokemon() throws Exception {
        String id = "001";

        ResponseEntity response = new ResponseEntity(pokemon01, HttpStatus.OK);
        when(pokemonService.updatePokemon(id, pokemon01)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/v1/pokemon/" + id).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(pokemon01));
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Pokemon pokemonResult = new Gson().fromJson(result, Pokemon.class);

        assertEquals(pokemonResult, pokemon01);
    }

    @Test
    void deletePokemonById() throws Exception {
        String id = "001";

        ResponseEntity response = new ResponseEntity(pokemon01, HttpStatus.OK);
        when(pokemonService.deletePokemonById(id)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/pokemon/" + id);
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Pokemon pokemonResult = new Gson().fromJson(result, Pokemon.class);

        assertEquals(pokemonResult, pokemon01);
    }
}
