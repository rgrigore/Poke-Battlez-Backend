package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.model.Category;
import com.example.pokebattlez.battle.model.BattlePokemon;
import com.example.pokebattlez.battle.model.Type;
import com.example.pokebattlez.battle.model.WeaknessChart;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BattleUtils {
    private static final double[] TYPE_MODIFIERS = {0, 2.5, 5, 10, 20, 40};

    private static final double STAB_MODIFIER = 1.5;

    private static final int LOWER_RANDOM_BOUND = 217;
    private static final int UPPER_RANDOM_BOUND = 255;

    public int getAttackTypeValueForMoveCategory(BattlePokemon attacker, Category moveCategory) {
        if (moveCategory == Category.STATUS) {
            return 0;
        }

        return attacker.getStats().get(moveCategory.getAttack()).getValue();
    }

    public int getDefenseTypeValueForMoveCategory(BattlePokemon defender, Category moveCategory) {
        if (moveCategory == Category.STATUS) {
            return 0;
        }

        return defender.getStats().get(moveCategory.getDefense()).getValue();
    }

    public int getTypeModifierIndex(Type attacking, List<Type> defending) {
        return defending.stream().mapToInt(defend -> WeaknessChart.getInstance().get(attacking).get(defend)).sum();
    }

    public double calculateTypeModifier(Type attacking, List<Type> defending) {
        int modifier = 3 + getTypeModifierIndex(attacking, defending);
        return TYPE_MODIFIERS[Math.max(modifier, 0)];
    }

    public double stabModifier(Type attackType, List<Type> pokemonTypes) {
        if (pokemonTypes.contains(attackType)) {
            return STAB_MODIFIER;
        }
        return 1;
    }

    public int randomAttackModifier() {
        return ThreadLocalRandom.current().nextInt(LOWER_RANDOM_BOUND, UPPER_RANDOM_BOUND + 1);
    }
}
