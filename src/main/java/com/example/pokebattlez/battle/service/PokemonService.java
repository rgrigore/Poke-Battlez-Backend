package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.model.BattlePokemon;
import com.example.pokebattlez.battle.model.Category;
import com.example.pokebattlez.battle.model.Stat;
import com.example.pokebattlez.battle.model.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private final RestTemplate restTemplate;

    public PokemonService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public void getApiData(BattlePokemon battlePokemon) {
        ApiPokemon apiPokemon = restTemplate.getForObject(
                String.format("https://pokeapi.co/api/v2/pokemon/%s", battlePokemon.getName()),
                ApiPokemon.class
        );

        assert apiPokemon != null;
        setStats(battlePokemon.getStats(), apiPokemon.stats);
        setTypes(battlePokemon, apiPokemon.types);
        setMoves(battlePokemon);
    }

    private void setStats(Map<Stat, BattlePokemon.Stat> pokemonStats, List<ApiStat> apiStats) {
        apiStats.forEach(apiStat ->
                pokemonStats
                        .get(Stat.valueOf(apiStat.stat.name.toUpperCase().replace('-', '_')))
                        .setBase(apiStat.base_stat)
        );
    }

    private void setTypes(BattlePokemon battlePokemon, List<ApiType> apiTypes) {
        battlePokemon.setTypes(apiTypes.stream().map(apiType -> Type.valueOf(apiType.type.name.toUpperCase())).collect(Collectors.toList()));
    }

    private void setMoves(BattlePokemon battlePokemon) {
        battlePokemon.getMoveNames().entrySet().stream()
                .filter(entry -> !entry.getValue().equals("none"))
                .forEach(entry -> {
                    ApiMove apiMove = getApiMove(entry.getValue());
                    battlePokemon.registerMove(BattlePokemon.Move.builder()
                            .name(apiMove.name)
                            .position(entry.getKey())
                            .power(apiMove.power)
                            .acc(apiMove.accuracy)
                            .pp(apiMove.pp)
                            .type(Type.valueOf(apiMove.type.name.toUpperCase()))
                            .category(Category.valueOf(apiMove.damage_class.name.toUpperCase()))
                            .build()
                    );
                });
    }

    private ApiMove getApiMove(String moveName) {
        return restTemplate.getForObject(
                String.format("https://pokeapi.co/api/v2/move/%s/", moveName),
                ApiMove.class
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ApiPokemon {
        private List<ApiStat> stats;
        private List<ApiType> types;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ApiStat {
        private int base_stat;
        private ApiStat1 stat;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class ApiStat1 {
            private String name;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ApiType {
        private ApiType1 type;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class ApiType1 {
            private String name;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ApiMove {
        private String name;
        private int power;
        private int accuracy;
        private int pp;
        private Type type;
        private Category damage_class;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Type {
            private String name;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Category {
            private String name;
        }
    }
}
