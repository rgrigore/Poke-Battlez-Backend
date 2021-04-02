package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.model.BattlePokemon;
import com.example.pokebattlez.battle.service.BattleService;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Battle {

    private final UUID id = UUID.randomUUID();

    private final BattleService battleService;
    private final ActionManager actionManager;

    private final List<Long> trainers = new ArrayList<>();
    private final Map<Long, List<BattlePokemon>> pokemon = new HashMap<>();
    private final Map<Long, BattlePokemon> activePokemon = new HashMap<>(); // TODO This needs to be adjusted so each trainer can have multiple active pokemon

    private int turnCount = 0;
    private final List<Long> turnFaints = new ArrayList<>();
    private final List<String> turnLog = new ArrayList<>();
    private final List<Long> turnOrder = new ArrayList<>(); // This can be user for possible animations on the frontend

    private List<Long> winners;
    private List<Long> losers;

    @Builder
    public Battle(BattleService battleService, ActionManager actionManager) {
        this.battleService = battleService;
        this.actionManager = actionManager;
    }

    public void registerTrainer(Long trainerId, List<BattlePokemon> team) {
        this.trainers.add(trainerId);
        this.pokemon.put(trainerId, team);
        team.stream().min(Comparator.comparingInt(BattlePokemon::getPosition))
                .ifPresent(pokemon -> activePokemon.put(trainerId, pokemon));
    }

    public void executeTurn() {
        resetTurn();

        Iterator<BattleAction> actions = actionManager.getActions();

        while (actions.hasNext()) {
            BattleAction action = actions.next();
            if (trainerActivePokemonNotFainted(action.getTrainerId())) {
                action.run();
                resolveAction(action.getTrainerId(), action.getLog(), action.getFaints());
            }
        }

    }

    private void resetTurn() {
        turnCount++;
        turnLog.clear();
        turnOrder.clear();
        turnFaints.clear();
    }

    private void resolveAction(Long trainerId, List<String> actionLog, List<Long> faints) {
        turnLog.addAll(actionLog);
        // This is where damage statuses / recoil would activate their effects
        turnLog.add("");
        turnOrder.add(trainerId);
        turnFaints.addAll(faints);
    }

    private boolean trainerActivePokemonNotFainted(Long trainerId) {
        return activePokemon.get(trainerId).getCurrentHp() > 0;
    }

    public boolean battleIsOver() { // TODO This needs to be adjusted for more then 2 trainers
        var partition = pokemon.entrySet().stream()
                .collect(Collectors.partitioningBy(entry ->
                        entry.getValue().stream().anyMatch(pokemon1 -> pokemon1.getCurrentHp() > 0)
                ));

        if (partition.get(true).size() == 1) {
            winners = partition.get(true).stream().map(Map.Entry::getKey).collect(Collectors.toList());
            losers = partition.get(false).stream().map(Map.Entry::getKey).collect(Collectors.toList());
            return true;
        }

        return false;
    }

//    TODO This should be handled by BattleService
//    public void connectTrainer(Long trainer) {
//        connectedTrainers.add(trainer);
//        if (connectedTrainers.containsAll(trainers)) {
//            startBattle();
//        }
//    }
//
//    public void startBattle() {
//        pokemon.forEach((trainer, team) -> battleService.sendTeam(this.id.toString(), trainer, team));
//        battleService.sendBattleState(this);
//    }
}
