package com.heytrade.pokedex.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Evolution {
    @Id
    private String id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 15)
    @Indexed(unique=true)
    private String name;
}
