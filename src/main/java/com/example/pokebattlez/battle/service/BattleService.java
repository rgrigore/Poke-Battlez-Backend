package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.controller.Battle;
import com.example.pokebattlez.battle.model.Type;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BattleService {
    private final static List<Battle> battles = new ArrayList<>();
    private final static Map<Type, Map<Type, Float>> weaknessChart;

    static {
        weaknessChart = Map.ofEntries(
                new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 0f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.WATER, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 0f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.ICE, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 0f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 0.5f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.POISON, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 2f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 0f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 0f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.BUG, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 0.5f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 0f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 0f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.DARK, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 0.5f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 2f)
                )),
                new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>(Type.NORMAL, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIRE, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.WATER, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ELECTRIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GRASS, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ICE, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FIGHTING, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.POISON, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GROUND, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FLYING, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.PSYCHIC, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.BUG, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.ROCK, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.GHOST, 1f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DRAGON, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.DARK, 2f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.STEEL, 0.5f),
                        new AbstractMap.SimpleImmutableEntry<>(Type.FAIRY, 1f)
                ))
        );
    }

    public void register(Battle battle) {
        battle.setBattleService(this);
        battles.add(battle);
    }
}
