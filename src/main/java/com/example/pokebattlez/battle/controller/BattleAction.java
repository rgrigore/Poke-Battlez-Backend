package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.model.BattlePokemon;
import com.example.pokebattlez.battle.model.Stat;
import com.example.pokebattlez.battle.model.TrainerAction;
import com.example.pokebattlez.battle.service.BattleUtils;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public abstract class BattleAction {

    protected BattleUtils battleUtils;
    public BattleUtils getBattleUtils() {
        if (battleUtils != null) {
            return battleUtils;
        }

        return BattleUtils.getInstance();
    }

    private final TrainerAction actionType = trainerAction();
    protected final int priority = priority();
    protected final Battle battle;
    protected final Long trainerId;

    private final List<Long> faints = new ArrayList<>();
    private final List<String> log = new ArrayList<>();

    public abstract void run();
    protected abstract TrainerAction trainerAction();
    protected abstract int priority();


    protected void log(String message) {
        log.add(message);
    }

    public static int calculateDamage(int level, int power, int attack, int defence, double stabModifier, double typeModifier, int randomModifier) {
        return (int) Math.floor(
                Math.floor(
                        Math.floor((
                                Math.floorDiv(Math.floorDiv(
                                        (Math.floorDiv(level * 2, 5) + 2) * attack * power
                                        , defence
                                ), 50) + 2
                        ) * stabModifier) * (typeModifier / 10)
                ) * randomModifier / 255
        );
    }


    public static void useMove(Move action, Battle battle) {
        BattleUtils utils = action.getBattleUtils();

        BattlePokemon attacker = battle.getActivePokemon().get(action.getTrainerId());
        BattlePokemon.Move move = attacker.getMoves().get(action.movePosition);

        action.log(String.format("%s used %s", attacker.getName(), move.getName()));

        int attack = utils.getAttackTypeValueForMoveCategory(attacker, move.getCategory());
        double stabModifier = utils.stabModifier(move.getType(), attacker.getTypes());
        int randomModifier = utils.randomAttackModifier(); // This might need to be rolled for each target

        action.targetTrainers.forEach(trainer -> {
            BattlePokemon defender = battle.getActivePokemon().get(trainer);
            int defence = utils.getDefenseTypeValueForMoveCategory(defender, move.getCategory());

            int typeModifierIndex = utils.getTypeModifierIndex(move.getType(), defender.getTypes());
            double typeModifier = utils.calculateTypeModifier(typeModifierIndex);

            if (typeModifierIndex > 0) {
                action.log("It's super effective!");
            } else if (typeModifierIndex < -2) {
                action.log(String.format("It had no effect on %s!", defender.getName()));
            } else if (typeModifierIndex < 0) {
                action.log("It's not very effective...");
            }

            int damage = calculateDamage(
                    attacker.getLevel(), move.getPower(),
                    attack, defence,
                    stabModifier, typeModifier, randomModifier
            );

            defender.setCurrentHp(defender.getCurrentHp() - damage);

            action.log(String.format("%s lost %d HP!(%d%% of it's health)",
                    defender.getName(), damage,
                    Math.floorDiv(damage * 100, defender.getStats().get(Stat.HP).getValue()))
            );

            if (defender.getCurrentHp() <= 0) {
                action.getFaints().add(trainer);
                action.log(String.format("%s fainted!", defender.getName()));
            }

        });
    }

    public static void doSwitch(Switch action, Battle battle) {
        action.log(String.format("Called back %s!",
                battle.getActivePokemon().get(action.getTrainerId()).getName()
        ));

        battle.getActivePokemon().replace(
                action.getTrainerId(),
                battle.getPokemon()
                        .get(action.getTrainerId()).stream()
                        .filter(pokemon -> pokemon.getPosition() == action.targetPosition).findFirst()
                        .orElseThrow(IllegalArgumentException::new)
        );

        action.log(String.format("Sent out %s!",
                battle.getActivePokemon().get(action.getTrainerId()).getName()
        ));
    }


    @SuperBuilder
    public static class Move extends BattleAction {
        private final List<Long> targetTrainers;
        private final int movePosition;

        @Override
        public void run() {
            BattleAction.useMove(this, battle);
        }

        @Override
        protected TrainerAction trainerAction() {
            return TrainerAction.MOVE;
        }
        @Override
        protected int priority() { // TODO This should also take into account priority moves
            return battle.getActivePokemon().get(trainerId).getStats().get(Stat.SPEED).getValue();
        }
    }

    @SuperBuilder
    public static class Switch extends BattleAction {
        private final int targetPosition;

        @Override
        public void run() {
            BattleAction.doSwitch(this, battle);
        }

        @Override
        protected TrainerAction trainerAction() {
            return TrainerAction.SWITCH;
        }
        @Override
        protected int priority() {
            return 10000;
        }
    }
}
