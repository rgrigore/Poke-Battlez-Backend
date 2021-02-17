package com.example.pokebattlez.battle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

import static com.example.pokebattlez.battle.model.Stat.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {
    private static final float NATURE_MODIFIER = 0.1f;

    private String name;
    private int position;

    private int level;
    private Nature nature;
    private List<Type> types;

    private Map<com.example.pokebattlez.battle.model.Stat, Stat> stats = Map.of(
            HP, new Stat(Stat::calculateHp),
            ATTACK, new Stat(),
            DEFENCE, new Stat(),
            SP_ATTACK, new Stat(),
            SP_DEFENCE, new Stat(),
            SPEED, new Stat()
    );

    private String gender;
    private String heldItem;
    private String ability;

    private String move1;
    private String move2;
    private String move3;
    private String move4;

    public void setLevel(int level) {
        this.level = level;
        stats.values().forEach(stat -> stat.setLevel(level));
    }

    public void setNature(Nature nature) {
        this.nature = nature;
        stats.get(nature.getUp()).modifyNature(NATURE_MODIFIER);
        stats.get(nature.getDown()).modifyNature(-NATURE_MODIFIER);
    }

    @Data
    @NoArgsConstructor
    public static class Stat {
        private int level;
        private int base;
        private int iv;
        private int ev;
        private int nature = 1;
        private int status = 1;

        private Function<Stat ,Integer> formula = Stat::calculateStat;

        private Stat(Function<Stat, Integer> formula) {
            this.formula = formula;
        }

        private void modifyNature(float value) {
            nature += value;
        }

        public int getValue() {
            try {
                return formula.apply(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        private static int calculateHp(Stat stat) {
            return Math.floorDiv((2 * stat.getBase() + stat.getIv() + Math.floorDiv(stat.getEv(), 4)) * stat.getLevel(), 100) + stat.getLevel() + 10;
        }

        private static int calculateStat(Stat stat) {
            return (int) Math.floor((Math.floorDiv((2 * stat.getBase() + stat.getIv() + Math.floorDiv(stat.getEv(), 4)) * stat.getLevel(), 100) + 5) * stat.getNature());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Move {
        private String name;

        private int position;

        private int power;
        private int acc;
        private int pp;
        private Type type;
        private Category category;
    }
}
