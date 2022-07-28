package com.heytrade.pokedex.domain.attack;

import com.heytrade.pokedex.domain.attack.impl.Fast;
import com.heytrade.pokedex.domain.attack.impl.Special;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class Attaks {

    private List<@Valid Fast> fast;
    private List<@Valid Special> special;
}
