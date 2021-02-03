package com.example.pokebattlez.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

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
    private Integer ivDefence;
    private Integer ivSpAttack;
    private Integer ivSpDefence;
    private Integer ivSpeed;

    private Integer evHp;
    private Integer evAttack;
    private Integer evDefence;
    private Integer evSpAttack;
    private Integer evSpDefence;
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
        setIvDefence(pokemon.getIvDefence());
        setIvSpAttack(pokemon.getIvSpAttack());
        setIvSpDefence(pokemon.getIvSpDefence());
        setIvSpeed(pokemon.getIvSpeed());
        setEvHp(pokemon.getEvHp());
        setEvAttack(pokemon.getEvAttack());
        setEvDefence(pokemon.getEvDefence());
        setEvSpAttack(pokemon.getEvSpAttack());
        setEvSpDefence(pokemon.getEvSpDefence());
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
