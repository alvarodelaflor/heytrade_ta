package com.heytrade.pokedex.repository;

import com.heytrade.pokedex.domain.Evolution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvolutionRepository extends MongoRepository<Evolution, String> {

    @Query("{ 'name' : ?0 }")
    Optional<Evolution> findEvolutionByName(String name);
}
