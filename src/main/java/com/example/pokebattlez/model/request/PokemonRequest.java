package com.example.pokebattlez.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonRequest {
    private long id;
    private long teamId;
    private int position;
    private String name;
    private int level;

    private Integer ivHp;
    private Integer ivAttack;
    private Integer ivDefense;
    private Integer ivSpAttack;
    private Integer ivSpDefense;
    private Integer ivSpeed;

    private Integer evHp;
    private Integer evAttack;
    private Integer evDefense;
    private Integer evSpAttack;
    private Integer evSpDefense;
    private Integer evSpeed;

    private String gender;
    private String nature;
    private String heldItem;
    private String ability;

    private String move1;
    private String move2;
    private String move3;
    private String move4;

    public PokemonRequest(com.example.pokebattlez.model.entity.Pokemon pokemon) {
        setId(pokemon.getId());
        setTeamId(pokemon.getTeam().getId());
        setPosition(pokemon.getPosition());
        setName(pokemon.getName());
        setLevel(pokemon.getLevel());
        setIvHp(pokemon.getIvHp());
        setIvAttack(pokemon.getIvAttack());
        setIvDefense(pokemon.getIvDefense());
        setIvSpAttack(pokemon.getIvSpAttack());
        setIvSpDefense(pokemon.getIvSpDefense());
        setIvSpeed(pokemon.getIvSpeed());
        setEvHp(pokemon.getEvHp());
        setEvAttack(pokemon.getEvAttack());
        setEvDefense(pokemon.getEvDefense());
        setEvSpAttack(pokemon.getEvSpAttack());
        setEvSpDefense(pokemon.getEvSpDefense());
        setEvSpeed(pokemon.getEvSpeed());
        setGender(pokemon.getGender());
        setNature(pokemon.getNature());
        setHeldItem(pokemon.getHeldItem());
        setAbility(pokemon.getAbility());
        setMove1(pokemon.getMove1());
        setMove2(pokemon.getMove2());
        setMove3(pokemon.getMove3());
        setMove4(pokemon.getMove4());
    }
}
