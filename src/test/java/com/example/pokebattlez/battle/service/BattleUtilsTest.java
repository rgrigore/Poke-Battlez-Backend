package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.model.BattlePokemon;
import com.example.pokebattlez.battle.model.Category;
import com.example.pokebattlez.battle.model.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.example.pokebattlez.battle.model.Stat.*;
import static com.example.pokebattlez.battle.model.Stat.SPEED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BattleUtilsTest {

    @Mock
    private BattlePokemon battlePokemon;

    @InjectMocks
    private BattleUtils battleUtils;

    @Test
    void getAttackTypeValueForPhysical() {
        BattlePokemon.StatData good = mock(BattlePokemon.StatData.class);
        BattlePokemon.StatData bad = mock(BattlePokemon.StatData.class);

        int goodOutput = 1;

        when(good.getValue()).thenReturn(goodOutput);

        when(battlePokemon.getStats()).thenReturn(Map.of(
                ATTACK, good,
                SPECIAL_ATTACK, bad
        ));

        assertThat(battleUtils.getAttackTypeValueForMoveCategory(battlePokemon, Category.PHYSICAL)).isEqualTo(goodOutput);
    }

    @Test
    void getAttackTypeValueForSpecial() {
        BattlePokemon.StatData good = mock(BattlePokemon.StatData.class);
        BattlePokemon.StatData bad = mock(BattlePokemon.StatData.class);

        int goodOutput = 1;

        when(good.getValue()).thenReturn(goodOutput);

        when(battlePokemon.getStats()).thenReturn(Map.of(
                ATTACK, bad,
                SPECIAL_ATTACK, good
        ));

        assertThat(battleUtils.getAttackTypeValueForMoveCategory(battlePokemon, Category.SPECIAL)).isEqualTo(goodOutput);
    }

    @Test
    void getAttackTypeValueForStatus() {
        assertThat(battleUtils.getAttackTypeValueForMoveCategory(battlePokemon, Category.STATUS)).isEqualTo(0);
    }

    @Test
    void getDefenseTypeValueForPhysical() {
        BattlePokemon.StatData good = mock(BattlePokemon.StatData.class);
        BattlePokemon.StatData bad = mock(BattlePokemon.StatData.class);

        int goodOutput = 1;

        when(good.getValue()).thenReturn(goodOutput);

        when(battlePokemon.getStats()).thenReturn(Map.of(
                DEFENSE, good,
                SPECIAL_DEFENSE, bad
        ));

        assertThat(battleUtils.getDefenseTypeValueForMoveCategory(battlePokemon, Category.PHYSICAL)).isEqualTo(goodOutput);
    }

    @Test
    void getDefenseTypeValueForSpecial() {
        BattlePokemon.StatData good = mock(BattlePokemon.StatData.class);
        BattlePokemon.StatData bad = mock(BattlePokemon.StatData.class);

        int goodOutput = 1;

        when(good.getValue()).thenReturn(goodOutput);

        when(battlePokemon.getStats()).thenReturn(Map.of(
                DEFENSE, bad,
                SPECIAL_DEFENSE, good
        ));

        assertThat(battleUtils.getDefenseTypeValueForMoveCategory(battlePokemon, Category.SPECIAL)).isEqualTo(goodOutput);
    }

    @Test
    void getDefenseTypeValueForStatus() {
        assertThat(battleUtils.getDefenseTypeValueForMoveCategory(battlePokemon, Category.STATUS)).isEqualTo(0);
    }

    @Test
    void stabModifierSingleType() {
        assertThat(battleUtils.stabModifier(Type.FIRE, List.of(Type.FIRE))).isEqualTo(1.5);
    }

    @Test
    void stabModifierMultipleTypes() {
        assertThat(battleUtils.stabModifier(Type.FIRE, List.of(Type.GRASS, Type.FIRE))).isEqualTo(1.5);
    }

    @Test
    void stabModifierFalse() {
        assertThat(battleUtils.stabModifier(Type.FIRE, List.of(Type.ICE, Type.GHOST))).isEqualTo(1);
    }
}