package com.heytrade.pokedex.domain;

import com.heytrade.pokedex.validation.HeightValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Height {

    @HeightValidator
    private String minimum;
    @HeightValidator
    private String maximum;
}
