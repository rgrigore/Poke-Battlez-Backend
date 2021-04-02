package com.example.pokebattlez.battle.model.packet;

import com.example.pokebattlez.battle.model.BattlePokemon;
import com.example.pokebattlez.battle.model.Stat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleStateDTO {
    private List<String> log;
    private List<Long> order;
    private Map<Long, Integer> active;
    private Map<Long, Map<Long, Integer>> currentHealths;
    private Map<Long, List<OpponentPokemon>> opponents;

    @Data
    public static class OpponentPokemon {
        private Long id;
        private String name;
        private int position;
        private boolean active;
        private String frontSprite;
        private int currentHp;
        private int maxHp;

        public OpponentPokemon(BattlePokemon pokemon) {
            id = pokemon.getId();
            name = pokemon.getName();
            position = pokemon.getPosition();
            frontSprite = pokemon.getFrontSprite();
            currentHp = pokemon.getCurrentHp();
            maxHp = pokemon.getStats().get(Stat.HP).getValue();
        }
    }
}
