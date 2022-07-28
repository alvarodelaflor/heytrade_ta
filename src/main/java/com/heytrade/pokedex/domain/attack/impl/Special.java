package com.heytrade.pokedex.domain.attack.impl;

import com.heytrade.pokedex.domain.attack.AttackTechnique;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode
public class Special implements AttackTechnique {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 35)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 35)
    private String type;
    @NotNull
    @Min(0)
    @Max(1000)
    private Integer damage;
}
