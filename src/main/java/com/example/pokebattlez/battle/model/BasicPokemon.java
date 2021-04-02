package com.example.pokebattlez.battle.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class BasicPokemon {
    private final Long id;
    private final String name;
    private final List<String> types;
    private final List<String> moves;
    private final int hp;
    private final int position;
    private final String frontSprite;
    private final String backSprite;

    public BasicPokemon(BattlePokemon pokemon) {
        id = pokemon.getId();
        name = pokemon.getName();
        types = pokemon.getTypes().stream().map(Type::toString).collect(Collectors.toList());
        moves = pokemon.getMoveNames().entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).collect(Collectors.toList());
        hp = pokemon.getStats().get(Stat.HP).getValue();
        position = pokemon.getPosition();
        frontSprite = pokemon.getFrontSprite();
        backSprite = pokemon.getBackSprite();


    }
}
