package com.example.pokebattlez.battle.model;

import com.example.pokebattlez.battle.service.PokemonService;
import com.example.pokebattlez.model.entity.Pokemon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Function;

import static com.example.pokebattlez.battle.model.Stat.*;

@Data
public class BattlePokemon {
    private static final float NATURE_MODIFIER = 0.1f;

    private PokemonService pokemonService;

    private Long id;
    private String name;
    private int position;

    private int level;
    private Nature nature;
    private List<Type> types;

    private int currentHp;

    public BattlePokemon(Pokemon pokemon) {
        id = pokemon.getId();
        name = pokemon.getName();
        position = pokemon.getPosition();
        gender = pokemon.getGender();
        heldItem = pokemon.getHeldItem();
        ability = pokemon.getAbility();

        setLevel(pokemon.getLevel());
        setNature(Nature.valueOf(pokemon.getNature().toUpperCase()));

        moveNames.put(0, pokemon.getMove1());
        moveNames.put(1, pokemon.getMove2());
        moveNames.put(2, pokemon.getMove3());
        moveNames.put(3, pokemon.getMove4());
    }

    private Map<com.example.pokebattlez.battle.model.Stat, Stat> stats = Map.of(
            HP, new Stat(Stat::calculateHp),
            ATTACK, new Stat(),
            DEFENCE, new Stat(),
            SPECIAL_ATTACK, new Stat(),
            SPECIAL_DEFENCE, new Stat(),
            SPEED, new Stat()
    );

    private Map<Integer, String> moveNames = new HashMap<>();
    private List<Move> moves = new ArrayList<>();

    private String gender;
    private String heldItem;
    private String ability;

    public void setLevel(int level) {
        this.level = level;
        stats.values().forEach(stat -> stat.setLevel(level));
    }

    public void setNature(Nature nature) {
        this.nature = nature;
        stats.get(nature.getUp()).modifyNature(NATURE_MODIFIER);
        stats.get(nature.getDown()).modifyNature(-NATURE_MODIFIER);
    }

    public void setPokemonService(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
        pokemonService.getApiData(this);
        currentHp = stats.get(HP).getValue();
    }

    public void registerMove(Move move) {
        moves.add(move);
    }

    @Data
    @NoArgsConstructor
    public static class Stat {
        private int level;

        private Integer value;
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
            return Objects.requireNonNullElseGet(stat.value, () -> {
                stat.value = Math.floorDiv((2 * stat.getBase() + stat.getIv() + Math.floorDiv(stat.getEv(), 4)) * stat.getLevel(), 100) + stat.getLevel() + 10;
                return stat.value;
            });
        }

        private static int calculateStat(Stat stat) {
            return (int) Math.floor((Math.floorDiv((2 * stat.getBase() + stat.getIv() + Math.floorDiv(stat.getEv(), 4)) * stat.getLevel(), 100) + 5) * stat.getNature());
        }
    }

    @Data
    @Builder
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
