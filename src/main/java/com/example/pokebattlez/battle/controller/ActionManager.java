package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.model.TrainerAction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ActionManager {

    private final List<BattleAction> registeredActions = new ArrayList<>();

    public void registerAction(BattleAction action) {
        if (isValidAction(action)) {
            registeredActions.add(action);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Iterator<BattleAction> getActions() {
        return registeredActions.stream().sorted(Comparator.comparingInt(BattleAction::getPriority).reversed()).iterator();
    }

    public void cancelTrainerAction(Long trainerId) {
        registeredActions.stream()
                .filter(action -> action.getTrainerId().equals(trainerId)).findFirst()
                .ifPresent(registeredActions::remove);
    }

    public void clearActions() {
        registeredActions.clear();
    }

    public List<Long> getRegisteredTrainers() {
        return registeredActions.stream().map(BattleAction::getTrainerId).distinct().collect(Collectors.toUnmodifiableList());
    }

    // region Abstract methods
    public abstract List<TrainerAction> getAvailableActionTypes();
    public abstract boolean isValidAction(BattleAction action);
    // endregion

    // region Type getters
    public static ActionManager standard() {
        return new Standard();
    }
    // endregion

    private static class Standard extends ActionManager {

        private static final List<TrainerAction> availableActionTypes = List.of(
                TrainerAction.MOVE,
                TrainerAction.SWITCH,
                TrainerAction.SWITCH_FAINTED
        );

        @Override
        public List<TrainerAction> getAvailableActionTypes() {
            return availableActionTypes;
        }

        @Override
        public boolean isValidAction(BattleAction action) {
            return availableActionTypes.contains(action.getActionType());
        }
    }
}
