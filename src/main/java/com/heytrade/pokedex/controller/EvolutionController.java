package com.heytrade.pokedex.controller;

import com.heytrade.pokedex.domain.Evolution;
import com.heytrade.pokedex.service.EvolutionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/evolution")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EvolutionController {

    private final EvolutionService evolutionService;

    @GetMapping()
    @ResponseBody
    public ResponseEntity getById(@RequestParam(required = false) String id, @RequestParam(required = false) String name) {
        ResponseEntity res = null;

        if (id == null && name == null) {
            res = this.evolutionService.findAll();
        } else if (id != null && name == null) {
            res = this.evolutionService.findEvolutionById(id);
        } else if (id == null && name != null) {
            res = this.evolutionService.findEvolutionByName(name);
        } else if (id != null && name != null) {
            Evolution evolution1 = this.evolutionService.findEvolutionById(id).getBody();
            Evolution evolution2 = this.evolutionService.findEvolutionByName(name).getBody();

            if (evolution1 != null && evolution2 != null && evolution1.equals(evolution2)) {
                res = new ResponseEntity<>(evolution1, HttpStatus.OK);
            } else {
                res = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return res;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity saveEvolution(@Valid @RequestBody Evolution evolution) {
        return this.evolutionService.saveEvolution(evolution);
    }

    @PutMapping ("/{id}")
    @ResponseBody
    public ResponseEntity<Evolution> updateEvolution(@PathVariable String id, @Valid @RequestBody Evolution evolution) {
        return this.evolutionService.updateEvolution(id, evolution);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<Evolution> deleteEvolutionById(@PathVariable String id) {
        return this.evolutionService.deleteEvolutionById(id);
    }
}
