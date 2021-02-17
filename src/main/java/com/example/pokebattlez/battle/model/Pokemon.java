package com.example.pokebattlez.battle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.Callable;

import static com.example.pokebattlez.battle.model.Stat.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {
    private static final float NATURE_MODIFIER = 0.1f;

    private String name;
    private int position;

    private int level;

    private Map<com.example.pokebattlez.battle.model.Stat, Stat> stats = Map.of(
            HP, new Stat(Stat::calculateHp),
            ATTACK, new Stat(),
            DEFENCE, new Stat(),
            SP_ATTACK, new Stat(),
            SP_DEFENCE, new Stat(),
            SPEED, new Stat()
    );

    private String gender;
    private String nature;
    private String heldItem;
    private String ability;

    private String move1;
    private String move2;
    private String move3;
    private String move4;

    public void setNature(Nature nature) {
        stats.get(nature.getUp()).modifyNature(NATURE_MODIFIER);
        stats.get(nature.getDown()).modifyNature(-NATURE_MODIFIER);
    }

    @Data
    @NoArgsConstructor
    public static class Stat {
        private int base;
        private int iv;
        private int ev;
        private int nature = 1;
        private int status = 1;

        private Callable<Integer> formula = Stat::calculateStat;

        private Stat(Callable<Integer> formula) {
            this.formula = formula;
        }

        private void modifyNature(float value) {
            nature += value;
        }

        public int getValue() {
            try {
                return formula.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        private static int calculateHp() {
            return 0; // TODO
        }

        private static int calculateStat() {
            return 0; // TODO
        }
    }
}
