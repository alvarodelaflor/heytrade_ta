package com.heytrade.pokedex.controller;

import com.google.gson.Gson;
import com.heytrade.pokedex.domain.Evolution;
import com.heytrade.pokedex.service.impl.EvolutionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EvolutionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EvolutionServiceImpl evolutionService;

    private Evolution evolution01;
    private Evolution evolution02;
    private List<Evolution> evolutions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        evolution01 = new Evolution("001", "Ivysaur");
        evolution02 = new Evolution("002", "Venusaur");

        evolutions = Arrays.asList(evolution01, evolution02);
    }

    @Test
    void getAll() throws Exception {
        ResponseEntity response = new ResponseEntity(evolutions, HttpStatus.OK);
        when(evolutionService.findAll()).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/evolution");
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        List<Evolution> evolutionsResult = Arrays.asList(new Gson().fromJson(result, Evolution[].class));

        assertEquals(evolutionsResult, evolutions);
    }

    @Test
    void getById() throws Exception {
        String id = "001";

        ResponseEntity response = new ResponseEntity(evolution01, HttpStatus.OK);
        when(evolutionService.findEvolutionById(id)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/evolution").param("id", id);
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Evolution evolutionResult = new Gson().fromJson(result, Evolution.class);

        assertEquals(evolutionResult, evolution01);
    }

    @Test
    void getByName() throws Exception {
        String name = "Ivysaur";

        ResponseEntity response = new ResponseEntity(evolution01, HttpStatus.OK);
        when(evolutionService.findEvolutionByName(name)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/evolution").param("name", name);
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Evolution evolutionResult = new Gson().fromJson(result, Evolution.class);

        assertEquals(evolutionResult, evolution01);
    }

    @Test
    void getByIDAndName() throws Exception {
        String id = "001";
        String name = "Ivysaur";

        ResponseEntity response01 = new ResponseEntity(evolution01, HttpStatus.OK);
        when(evolutionService.findEvolutionByName(name)).thenReturn(response01);

        ResponseEntity response02 = new ResponseEntity(evolution01, HttpStatus.OK);
        when(evolutionService.findEvolutionById(id)).thenReturn(response02);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/evolution").param("name", name).param("id", id);
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Evolution evolutionResult = new Gson().fromJson(result, Evolution.class);

        assertEquals(evolutionResult, evolution01);
    }

    @Test
    void getByIDAndName400() throws Exception {
        String id = "001";
        String name = "Ivysaur";

        ResponseEntity response01 = new ResponseEntity(evolution01, HttpStatus.OK);
        when(evolutionService.findEvolutionByName(name)).thenReturn(response01);

        ResponseEntity response02 = new ResponseEntity(evolution02, HttpStatus.OK);
        when(evolutionService.findEvolutionById(id)).thenReturn(response02);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/evolution").param("name", name).param("id", id);
        mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(status().isBadRequest());

    }

    @Test
    void saveEvolution() throws Exception {
        ResponseEntity response = new ResponseEntity(evolution01, HttpStatus.OK);
        when(evolutionService.saveEvolution(evolution01)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/evolution").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(evolution01));
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Evolution evolutionResult = new Gson().fromJson(result, Evolution.class);

        assertEquals(evolutionResult, evolution01);
    }

    @Test
    void updateEvolution() throws Exception {
        String id = "001";

        ResponseEntity response = new ResponseEntity(evolution01, HttpStatus.OK);
        when(evolutionService.updateEvolution(id, evolution01)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/v1/evolution/" + id).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(evolution01));
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Evolution evolutionResult = new Gson().fromJson(result, Evolution.class);

        assertEquals(evolutionResult, evolution01);
    }

    @Test
    void deleteEvolutionById() throws Exception {
        String id = "001";

        ResponseEntity response = new ResponseEntity(evolution01, HttpStatus.OK);
        when(evolutionService.deleteEvolutionById(id)).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/evolution/" + id);
        String result = mvc.perform(request).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Evolution evolutionResult = new Gson().fromJson(result, Evolution.class);

        assertEquals(evolutionResult, evolution01);
    }
}
