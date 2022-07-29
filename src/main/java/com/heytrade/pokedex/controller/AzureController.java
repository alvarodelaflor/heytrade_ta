package com.heytrade.pokedex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AzureController {

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity("hello", HttpStatus.OK);
    }
}
