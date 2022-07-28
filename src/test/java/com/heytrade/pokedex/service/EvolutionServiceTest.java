package com.heytrade.pokedex.service;

import com.google.gson.Gson;
import com.heytrade.pokedex.domain.Evolution;
import com.heytrade.pokedex.repository.EvolutionRepository;
import com.heytrade.pokedex.service.impl.EvolutionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class EvolutionServiceTest {

    @Mock
    private EvolutionRepository evolutionRepository;

    @InjectMocks
    private EvolutionServiceImpl evolutionService;

    private Evolution evolution01;
    private Evolution evolution02;
    private List<Evolution> evolutions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        evolution01 = new Evolution("3", "Ivysaur");
    }

    @Test
    void findAll() {
        when(evolutionRepository.findAll()).thenReturn(Arrays.asList(evolution01));
        assertNotNull(evolutionService.findAll());
    }

    @Test
    void findById404() {
        Optional<Evolution> optional = Optional.empty();
        String id = "3";

        when(evolutionRepository.findById(id)).thenReturn(optional);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        assertEquals(this.evolutionService.findEvolutionById(id), responseEntity);
    }

    @Test
    void findById200() {
        Optional<Evolution> optional = Optional.of(evolution01);
        String id = "3";

        when(evolutionRepository.findById(id)).thenReturn(optional);
        ResponseEntity responseEntity = new ResponseEntity(optional.get(), HttpStatus.OK);
        assertEquals(this.evolutionService.findEvolutionById(id), responseEntity);
    }

    @Test
    void findByName404() {
        Optional<Evolution> optional = Optional.empty();
        String name = "Ivysaur";

        when(evolutionRepository.findById(name)).thenReturn(optional);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        assertEquals(this.evolutionService.findEvolutionById(name), responseEntity);
    }

    @Test
    void findByName200() {
        Optional<Evolution> optional = Optional.of(evolution01);
        String name = "Ivysaur";

        when(evolutionRepository.findById(name)).thenReturn(optional);
        ResponseEntity responseEntity = new ResponseEntity(optional.get(), HttpStatus.OK);
        assertEquals(this.evolutionService.findEvolutionById(name), responseEntity);
    }

    @Test
    void saveEvolution() {
        when(this.evolutionRepository.save(evolution01)).thenReturn(evolution01);

        ResponseEntity responseEntity = new ResponseEntity(evolution01, HttpStatus.OK);

        assertEquals(this.evolutionService.saveEvolution(evolution01), responseEntity);
    }

    @Test
    void updateEvolution200() {
        String id = "3";

        when(this.evolutionRepository.findById(id)).thenReturn(Optional.of(evolution01));
        when(this.evolutionRepository.save(evolution01)).thenReturn(evolution01);

        ResponseEntity responseEntity = new ResponseEntity(evolution01, HttpStatus.OK);

        assertEquals(this.evolutionService.updateEvolution(id, evolution01), responseEntity);
    }

    @Test
    void updateEvolutionById404() {
        String id = "3";

        when(this.evolutionRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        assertEquals(this.evolutionService.updateEvolution(id, evolution01), responseEntity);
    }

    @Test
    void deleteEvolutionById200() {
        String id = "3";

        when(evolutionRepository.findById(id)).thenReturn(Optional.of(evolution01));

        ResponseEntity responseEntity = new ResponseEntity(evolution01, HttpStatus.OK);

        assertEquals(this.evolutionService.deleteEvolutionById(id), responseEntity);
    }

    @Test
    void deleteEvolutionById404() {
        String id = "3";

        when(evolutionRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        assertEquals(this.evolutionService.deleteEvolutionById(id), responseEntity);
    }

    @Test
    void deleteEvolutionById500() {
        String id = "3";

        when(evolutionRepository.findById(id)).thenThrow(NullPointerException.class);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(this.evolutionService.deleteEvolutionById(id), responseEntity);
    }

    @Test
    void deleteEvolutionByName200() {
        String name = "Ivysaur";

        when(evolutionRepository.findEvolutionByName(name)).thenReturn(Optional.of(evolution01));

        ResponseEntity responseEntity = new ResponseEntity(evolution01, HttpStatus.OK);

        assertEquals(this.evolutionService.deleteEvolutionByName(name), responseEntity);
    }

    @Test
    void deleteEvolution404() {
        String name = "Ivysaur";

        when(evolutionRepository.findEvolutionByName(name)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        assertEquals(this.evolutionService.deleteEvolutionByName(name), responseEntity);
    }

    @Test
    void deleteEvolution500() {
        String name = "Ivysaur";

        when(evolutionRepository.findEvolutionByName(name)).thenThrow(NullPointerException.class);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(this.evolutionService.deleteEvolutionByName(name), responseEntity);
    }
}
