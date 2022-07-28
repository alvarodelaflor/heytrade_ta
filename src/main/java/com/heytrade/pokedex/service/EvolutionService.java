package com.heytrade.pokedex.service;

import com.heytrade.pokedex.domain.Evolution;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EvolutionService {
    ResponseEntity<List<Evolution>> findAll();

    ResponseEntity<Evolution> findEvolutionByName(String name);

    ResponseEntity<Evolution> findEvolutionById(String id);

    ResponseEntity saveEvolution(Evolution evolution);

    ResponseEntity updateEvolution(String id, Evolution evolution);

    ResponseEntity<Evolution> deleteEvolutionById(String id);

    ResponseEntity<Evolution> deleteEvolutionByName(String name);
}
