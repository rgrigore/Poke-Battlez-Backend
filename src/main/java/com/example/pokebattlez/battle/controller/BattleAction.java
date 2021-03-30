package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.model.TrainerAction;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public abstract class BattleAction {

    private final TrainerAction actionType = trainerAction();

    private final int priority;
    private final Long trainerId;

    public abstract void run(Battle battle);
    protected abstract TrainerAction trainerAction();


    public static void useMove(Move action, Battle battle) {
        // TODO
    }

    public static void doSwitch(Switch action, Battle battle) {
        // TODO
    }


    @SuperBuilder
    public static class Move extends BattleAction {
        private final List<Long> targetTrainers;
        private final int movePosition;

        @Override
        public void run(Battle battle) {
            BattleAction.useMove(this, battle);
        }

        @Override
        protected TrainerAction trainerAction() {
            return TrainerAction.MOVE;
        }
    }

    @SuperBuilder
    public static class Switch extends BattleAction {
        private final int targetPosition;

        @Override
        public void run(Battle battle) {
            BattleAction.doSwitch(this, battle);
        }

        @Override
        protected TrainerAction trainerAction() {
            return TrainerAction.SWITCH;
        }
    }

    @SuperBuilder
    public static class SwitchFainted extends Switch {
        @Override
        protected TrainerAction trainerAction() {
            return TrainerAction.SWITCH_FAINTED;
        }
    }
}
