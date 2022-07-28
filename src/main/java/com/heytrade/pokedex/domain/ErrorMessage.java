package com.heytrade.pokedex.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private Boolean status;
    private LocalDateTime dateTime;
    private String message;
    private String description;
}
