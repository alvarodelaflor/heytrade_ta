package com.heytrade.pokedex.domain;

import com.heytrade.pokedex.validation.WeightValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Weight {

    @WeightValidator
    private String minimum;
    @WeightValidator
    private String maximum;
}
