package com.heytrade.pokedex.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.gson.Gson;
import com.heytrade.pokedex.domain.ErrorMessage;
import com.heytrade.pokedex.domain.Evolution;
import com.heytrade.pokedex.domain.Pokemon;
import com.heytrade.pokedex.service.impl.EvolutionServiceImpl;
import com.heytrade.pokedex.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.net.BindException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerExceptionHandlerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PokemonServiceImpl pokemonService;

    private Pokemon pokemon01;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        String pokemonJson01 = "{\"id\":\"002\",\"name\":\"Ivysaur\",\"classification\":\"SeedPokémon\",\"types\":[\"Grass\",\"Poison\"],\"resistant\":[\"Water\",\"Electric\",\"Grass\",\"Fighting\",\"Fairy\"],\"weaknesses\":[\"Fire\",\"Ice\",\"Flying\",\"Psychic\"],\"weight\":{\"minimum\":\"11.38kg\",\"maximum\":\"14.63kg\"},\"height\":{\"minimum\":\"0.88m\",\"maximum\":\"1.13m\"},\"fleeRate\":0.07,\"previousEvolutions\":[{\"id\":1,\"name\":\"Bulbasaur\"}],\"evolutionRequirements\":{\"amount\":100,\"name\":\"Bulbasaurcandies\"},\"evolutions\":[{\"id\":3,\"name\":\"Venusaur\"}],\"maxCP\":1483,\"maxHP\":1632,\"attacks\":{\"fast\":[{\"name\":\"RazorLeaf\",\"type\":\"Grass\",\"damage\":15},{\"name\":\"VineWhip\",\"type\":\"Grass\",\"damage\":7}],\"special\":[{\"name\":\"PowerWhip\",\"type\":\"Grass\",\"damage\":70},{\"name\":\"SludgeBomb\",\"type\":\"Poison\",\"damage\":55},{\"name\":\"SolarBeam\",\"type\":\"Grass\",\"damage\":120}]}}";
        pokemon01 = new Gson().fromJson(pokemonJson01, Pokemon.class);
    }

    @Test
    void handleDuplicateKeyExceptionVException() throws Exception {

        when(this.pokemonService.savePokemon(pokemon01)).thenThrow(new DuplicateKeyException("dup key: da'"));

        mvc.perform(post("/api/v1/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(pokemon01))
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof DuplicateKeyException));
    }
}
