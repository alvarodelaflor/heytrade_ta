package com.heytrade.pokedex.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
public class EvolutionRequirements {

    @Min(0)
    @Max(20000)
    private Integer amount;
    @Size(min = 3, max = 35)
    private String name;
}
