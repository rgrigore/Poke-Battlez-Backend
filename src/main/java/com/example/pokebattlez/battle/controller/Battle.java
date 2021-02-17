package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.model.Pokemon;
import com.example.pokebattlez.battle.model.Stat;
import com.example.pokebattlez.battle.service.BattleService;
import com.example.pokebattlez.battle.service.BattleUtils;
import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.function.BiConsumer;

@Data
public class Battle {
    private UUID id;

    private final List<Long> trainers = new ArrayList<>();
    private final Map<Long, List<Pokemon>> pokemon = new HashMap<>();
    private final Map<Long, Pokemon> activePokemon = new HashMap<>();
    private final List<PlayerAction> playerActions = new ArrayList<>();

    private BattleService battleService;
    private static final BattleUtils battleUtils = new BattleUtils(); // Ask about this

    public void registerPokemonTeam(Long trainerId, List<Pokemon> pokemon) {
        this.pokemon.put(trainerId, pokemon);
    }

    public void registerSwitchPokemon(Long trainerId, int position) {
        playerActions.add(PlayerAction.builder()
                .trainerId(trainerId)
                .switchToPosition(position)
                .action(Battle::switchPokemon)
                .priority(1000)
                .build()
        );
    }

    public void registerUseMove(Long trainerId, int position) {
        playerActions.add(PlayerAction.builder()
                .trainerId(trainerId)
                .moveInPosition(position)
                .action(Battle::useMove)
                .priority(activePokemon.get(trainerId).getStats().get(Stat.SPEED).getValue())
                .build()
        );
    }

    public void cancelAction(Long trainerId) {
        playerActions.stream()
                .filter(playerAction -> playerAction.trainerId.equals(trainerId)).findFirst()
                .ifPresent(playerActions::remove);
    }

    private static void switchPokemon(Battle battle, PlayerAction playerAction) {
        battle.pokemon.get(playerAction.trainerId).stream()
                .filter(pokemon1 -> pokemon1.getPosition() == playerAction.switchToPosition).findFirst()
                .ifPresent(pokemon1 -> battle.activePokemon.replace(playerAction.trainerId, pokemon1));
        battle.playerActions.remove(playerAction);
    }

    private static void useMove(Battle battle, PlayerAction playerAction) {
        // TODO
    }

    private int calculateDamage(Pokemon attacker, Pokemon defender, Pokemon.Move move) {
        return (int) Math.floor((Math.floor(
                Math.floor(
                        (Math.floorDiv(Math.floorDiv((Math.floorDiv(attacker.getLevel() * 2, 5) + 2) * battleUtils.getAttackTypeValueForMoveCategory(attacker, move.getCategory()) * move.getPower(), battleUtils.getDefenceTypeValueForMoveCategory(defender, move.getCategory()) ), 50) + 2) * battleUtils.stabModifier(move.getType(), attacker.getTypes())
                ) *
                battleUtils.calculateTypeModifier(move.getType(), defender.getTypes()) / 10
        ) * battleUtils.randomAttackModifier()) / 255);
    }

    @Builder
    private static class PlayerAction {
        private final int priority;

        private final Long trainerId;
        private final int switchToPosition;
        private final int moveInPosition;

        private final BiConsumer<Battle, PlayerAction> action;
    }
}
