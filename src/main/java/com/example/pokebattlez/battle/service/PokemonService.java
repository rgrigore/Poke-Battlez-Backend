package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.model.Pokemon;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.pokebattlez.battle.model.Stat.*;

@Service
public class PokemonService {

    private final RestTemplate restTemplate;

    public PokemonService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public void getBaseStats(Pokemon pokemon) {
        ApiPokemon apiPokemon = restTemplate.getForObject(
                String.format("https://pokeapi.co/api/v2/pokemon/%s", pokemon.getName()),
                ApiPokemon.class
        );

        assert apiPokemon != null;
        pokemon.getStats().get(HP).setBase(apiPokemon.getStats().get(0).getBase_stat());
        pokemon.getStats().get(ATTACK).setBase(apiPokemon.getStats().get(1).getBase_stat());
        pokemon.getStats().get(DEFENCE).setBase(apiPokemon.getStats().get(2).getBase_stat());
        pokemon.getStats().get(SP_ATTACK).setBase(apiPokemon.getStats().get(3).getBase_stat());
        pokemon.getStats().get(SP_DEFENCE).setBase(apiPokemon.getStats().get(4).getBase_stat());
        pokemon.getStats().get(SPEED).setBase(apiPokemon.getStats().get(5).getBase_stat());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ApiPokemon {
        private List<ApiStat> stats;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ApiStat {
        private int base_stat;
    }
}
