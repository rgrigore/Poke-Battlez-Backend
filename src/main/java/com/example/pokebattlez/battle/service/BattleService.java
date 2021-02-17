package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.controller.Battle;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BattleService {
    private final static List<Battle> battles = new ArrayList<>();

    public void register(Battle battle) {
        battle.setBattleService(this);
        battles.add(battle);
    }

    public void useMove(String battleId ,Long trainerId, int movePosition) {
        findBattle(battleId).ifPresent(battle -> battle.registerUseMove(trainerId, movePosition));
    }

    public void switchPokemon(String battleId, Long trainerId, int pokemonPosition) {
        findBattle(battleId).ifPresent(battle -> battle.registerSwitchPokemon(trainerId, pokemonPosition));
    }

    public void cancelAction(String battleId, Long trainerId) {
        findBattle(battleId).ifPresent(battle -> battle.cancelAction(trainerId));
    }

    public Optional<Battle> findBattle(String battleId) {
        return battles.stream().filter(battle -> battle.getId().toString().equals(battleId)).findFirst();
    }
}
