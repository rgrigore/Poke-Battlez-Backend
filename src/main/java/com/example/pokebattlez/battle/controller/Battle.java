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

    private static final String MOVE_USED_LOG = "%s used %s!";
    private static final String LOST_HP_LOG = "%s lost %d HP!(%d%% of it's health)";

    private UUID id;

    private final List<Long> trainers = new ArrayList<>();
    private final Map<Long, List<Pokemon>> pokemon = new HashMap<>();
    private final Map<Long, Pokemon> activePokemon = new HashMap<>();
    private final List<PlayerAction> playerActions = new ArrayList<>();

    private BattleService battleService;
    private static final BattleUtils battleUtils = new BattleUtils(); // Ask about this

    private List<Long> waitingForTrainers;
    private List<String> turnLog;

    public void registerPokemonTeam(Long trainerId, List<Pokemon> pokemon) {
        this.pokemon.put(trainerId, pokemon);
    }

    public void registerSwitchPokemon(Long trainerId, int position) {
        if (waitingForTrainers.contains(trainerId)) {
            playerActions.add(PlayerAction.builder()
                    .trainerId(trainerId)
                    .switchToPosition(position)
                    .action(Battle::switchPokemon)
                    .priority(1000)
                    .build()
            );
            waitingForTrainers.remove(trainerId);
        }
    }

    public void registerUseMove(Long trainerId, int movePosition, Long moveTarget) {
        if (waitingForTrainers.contains(trainerId)) {
            playerActions.add(PlayerAction.builder()
                    .trainerId(trainerId)
                    .moveInPosition(movePosition)
                    .moveTarget(moveTarget)
                    .action(Battle::useMove)
                    .priority(activePokemon.get(trainerId).getStats().get(Stat.SPEED).getValue())
                    .build()
            );
            waitingForTrainers.remove(trainerId);
        }
    }

    public void cancelAction(Long trainerId) {
        if (trainers.contains(trainerId) && !waitingForTrainers.contains(trainerId)) {
            playerActions.stream()
                    .filter(playerAction -> playerAction.trainerId.equals(trainerId)).findFirst()
                    .ifPresent(playerActions::remove);
            waitingForTrainers.add(trainerId);
        }
    }

    private static void switchPokemon(Battle battle, PlayerAction playerAction) {
        battle.turnLog.add(String.format("Called back %s!",
                battle.activePokemon.get(playerAction.trainerId).getName()
        ));

        battle.pokemon.get(playerAction.trainerId).stream()
                .filter(pokemon1 -> pokemon1.getPosition() == playerAction.switchToPosition).findFirst()
                .ifPresent(pokemon1 -> battle.activePokemon.replace(playerAction.trainerId, pokemon1));

        battle.turnLog.add(String.format("Sent out %s!",
                battle.activePokemon.get(playerAction.trainerId).getName()
        ));

        battle.playerActions.remove(playerAction);
    }

    private static void useMove(Battle battle, PlayerAction playerAction) {
        Pokemon attacker = battle.activePokemon.get(playerAction.trainerId);
        Pokemon defender = battle.activePokemon.values().stream().filter(pokemon1 -> pokemon1.getId().equals(playerAction.moveTarget)).findFirst().orElse(null);

        Pokemon.Move move = attacker.getMoves().stream().filter(move1 -> move1.getPosition() == playerAction.moveInPosition).findFirst().orElse(null);

        if (defender != null && move != null) {
            int damage = battle.calculateDamage(attacker, defender, move);

            defender.setCurrentHp(defender.getCurrentHp() - damage);

            battle.turnLog.add(String.format(MOVE_USED_LOG,
                    attacker.getName(),
                    move.getName()
            ));

            int typeModifierIndex = battleUtils.getTypeModifierIndex(move.getType(), defender.getTypes());
            if (typeModifierIndex > 0) {
                battle.turnLog.add("It's super effective!");
            } else if (typeModifierIndex < -2) {
                battle.turnLog.add(String.format("It had no effect on %s!", defender.getName()));
            } else if (typeModifierIndex < 0) {
                battle.turnLog.add("It's not very effective...");
            }

            battle.turnLog.add(String.format(LOST_HP_LOG,
                    defender.getName(),
                    damage,
                    Math.floorDiv(damage * 100, defender.getStats().get(Stat.HP).getValue())
            ));
        }

        battle.playerActions.remove(playerAction);
    }

    private int calculateDamage(Pokemon attacker, Pokemon defender, Pokemon.Move move) {
        return (int) Math.floor((Math.floor(
                Math.floor(
                        (Math.floorDiv(Math.floorDiv((Math.floorDiv(attacker.getLevel() * 2, 5) + 2) * battleUtils.getAttackTypeValueForMoveCategory(attacker, move.getCategory()) * move.getPower(), battleUtils.getDefenceTypeValueForMoveCategory(defender, move.getCategory()) ), 50) + 2) * battleUtils.stabModifier(move.getType(), attacker.getTypes())
                ) *
                battleUtils.calculateTypeModifier(move.getType(), defender.getTypes()) / 10
        ) * battleUtils.randomAttackModifier()) / 255);
    }

    private void playTurn() {
        turnLog = new ArrayList<>();

        playerActions.stream().sorted(Comparator.comparingInt(action -> action.priority)).forEachOrdered(playerAction -> playerAction.run(this));
        // TODO Manage fainting

        waitingForTrainers = List.copyOf(trainers);
    }

    @Builder
    private static class PlayerAction {
        private final int priority;

        private final Long trainerId;
        private final int switchToPosition;
        private final int moveInPosition;
        private final Long moveTarget;

        private final BiConsumer<Battle, PlayerAction> action;

        private void run(Battle battle) {
            action.accept(battle, this);
        }
    }
}
