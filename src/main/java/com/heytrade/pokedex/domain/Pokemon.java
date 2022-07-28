package com.heytrade.pokedex.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.heytrade.pokedex.domain.attack.Attaks;
import com.heytrade.pokedex.validation.ValidateDecimalConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class Pokemon {

    @Id
    private String id;
    @Indexed(unique=true)
    @NotNull
    @NotBlank
    @Size(min = 3, max = 15)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 35)
    private String classification;
    private List<@NotNull @NotBlank @Size(min = 3, max = 10) String> types;
    private List<@NotNull @NotBlank @Size(min = 3, max = 10) String> resistant;
    private List<@NotNull @NotBlank @Size(min = 3, max = 10) String> weaknesses;
    @NotNull
    @Valid
    private Weight weight;
    @NotNull
    @Valid
    private Height height;
    @NotNull
    @ValidateDecimalConstraint
    private Double fleeRate;
    @JsonProperty("Previous evolution(s)")
    @DBRef
    private List<@Valid Evolution> previousEvolutions;
    @NotNull
    @Valid
    private EvolutionRequirements evolutionRequirements;
    @DBRef
    private List<@Valid Evolution> evolutions;
    @NotNull
    @Min(0)
    @Max(10000)
    private Integer maxCP;
    @NotNull
    @Min(0)
    @Max(10000)
    private Integer maxHP;
    @NotNull
    @Valid
    private Attaks attacks;

}
