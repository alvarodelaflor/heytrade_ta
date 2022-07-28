package com.heytrade.pokedex.service.impl;

import com.heytrade.pokedex.domain.Evolution;
import com.heytrade.pokedex.repository.EvolutionRepository;
import com.heytrade.pokedex.service.EvolutionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EvolutionServiceImpl implements EvolutionService {
    EvolutionRepository evolutionRepository;

    @Cacheable("evolutions")
    @Override
    public ResponseEntity<List<Evolution>> findAll() {
        return new ResponseEntity<>(this.evolutionRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Evolution> findEvolutionByName(String name) {
        Optional<Evolution> evolution = this.evolutionRepository.findEvolutionByName(name);
        return evolution.isPresent() ? new ResponseEntity<>(evolution.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Evolution> findEvolutionById(String id) {
        Optional<Evolution> evolution = this.evolutionRepository.findById(id);
        return evolution.isPresent() ? new ResponseEntity<>(evolution.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity saveEvolution(Evolution evolution) {
        Evolution evolutionSaved = this.evolutionRepository.save(evolution);
        return new ResponseEntity(evolutionSaved, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateEvolution(String id, Evolution evolution) {
        ResponseEntity res;
        Optional<Evolution> evolutionFind = this.evolutionRepository.findById(id);
        if (evolutionFind.isPresent()) {
            evolution.setId(id);
            Evolution evolutionSaved = this.evolutionRepository.save(evolution);
            res = new ResponseEntity(evolutionSaved, HttpStatus.OK);
        } else {
            res = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @Override
    public ResponseEntity<Evolution> deleteEvolutionById(String id) {
        ResponseEntity<Evolution> res;
        try {
            Evolution evolution = this.findEvolutionById(id).getBody();
            if (evolution != null) {
                this.evolutionRepository.delete((Evolution) evolution);
                res = new ResponseEntity<>((Evolution) evolution, HttpStatus.OK);
            } else {
                res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            res = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @Override
    public ResponseEntity<Evolution> deleteEvolutionByName(String name) {
        ResponseEntity<Evolution> res;
        try {
            Evolution evolution = this.findEvolutionByName(name).getBody();
            if (evolution != null) {
                this.evolutionRepository.delete((Evolution) evolution);
                res = new ResponseEntity<>((Evolution) evolution, HttpStatus.OK);
            } else {
                res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            res = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }
}
