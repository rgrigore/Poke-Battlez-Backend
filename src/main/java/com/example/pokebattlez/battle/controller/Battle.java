package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.model.Pokemon;
import com.example.pokebattlez.battle.model.Stat;
import com.example.pokebattlez.battle.service.BattleService;
import lombok.Builder;
import lombok.Data;

import java.util.*;

public class Battle {
    private UUID id;

    private final List<Long> trainers = new ArrayList<>();
    private final Map<Long, List<Pokemon>> pokemon = new HashMap<>();
    private final Map<Long, Pokemon> activePokemon = new HashMap<>();
    private final List<PlayerAction> playerActions = new ArrayList<>();

    private BattleService battleService;

    public void setBattleService(BattleService battleService) {
        this.battleService = battleService;
    }

    public void registerPokemonTeam(Long trainerId, List<Pokemon> pokemon) {
        this.pokemon.put(trainerId, pokemon);
    }

    public void switchPokemonTo(Long trainerId, int position) {
        playerActions.add(PlayerAction.builder()
                .trainerId(trainerId)
                .switchToPosition(position)
                .action(PlayerAction::switchPokemon)
                .priority(1000)
                .build()
        );
    }

    public void useMoveInPosition(Long trainerId, int position) {
        playerActions.add(PlayerAction.builder()
                .trainerId(trainerId)
                .moveInPosition(position)
                .action(PlayerAction::useMove)
                .priority(activePokemon.get(trainerId).getStats().get(Stat.SPEED).getValue())
                .build()
        );
    }

    public void cancelAction(Long trainerId) {
        playerActions.stream().filter(playerAction -> playerAction.getTrainerId().equals(trainerId)).findFirst().ifPresent(playerActions::remove);
    }

    @Data
    @Builder
    private static class PlayerAction {
        private int priority;

        private Long trainerId;
        private int switchToPosition;
        private int moveInPosition;

        private Runnable action;

        private static void switchPokemon() {
            // TODO
        }

        private static void useMove() {
            // TODO
        }
    }
}
