package com.example.pokebattlez.model.entity;

import com.example.pokebattlez.model.request.PokemonRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pokemon {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne private Team team;
    @Column(nullable = false) private Integer position;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private Integer level;

    @Column(nullable = false) private Integer IvHp;
    @Column(nullable = false) private Integer IvAttack;
    @Column(nullable = false) private Integer IvDefense;
    @Column(nullable = false) private Integer IvSpAttack;
    @Column(nullable = false) private Integer IvSpDefense;
    @Column(nullable = false) private Integer IvSpeed;

    @Column(nullable = false) private Integer EvHp;
    @Column(nullable = false) private Integer EvAttack;
    @Column(nullable = false) private Integer EvDefense;
    @Column(nullable = false) private Integer EvSpAttack;
    @Column(nullable = false) private Integer EvSpDefense;
    @Column(nullable = false) private Integer EvSpeed;

    @Column(nullable = false) private String gender;
    @Column(nullable = false) private String nature;
    @Column(nullable = false) private String heldItem;
    @Column(nullable = false) private String ability;

    @Column(nullable = false) private String move1;
    @Column(nullable = false) private String move2;
    @Column(nullable = false) private String move3;
    @Column(nullable = false) private String move4;

    private Pokemon(PokemonRequest pokemonRequest) {
        updateFields(pokemonRequest);
    }

    public static Pokemon from(PokemonRequest pokemonRequest) {
        return new Pokemon(pokemonRequest);
    }

    @Transient
    public void updateFields(PokemonRequest pokemonRequest) {
        setPosition(pokemonRequest.getPosition());
        setName(pokemonRequest.getName());
        setLevel(pokemonRequest.getLevel());
        setIvHp(pokemonRequest.getIvHp());
        setIvAttack(pokemonRequest.getIvAttack());
        setIvDefense(pokemonRequest.getIvDefense());
        setIvSpAttack(pokemonRequest.getIvSpAttack());
        setIvSpDefense(pokemonRequest.getIvSpDefense());
        setIvSpeed(pokemonRequest.getIvSpeed());
        setEvHp(pokemonRequest.getEvHp());
        setEvAttack(pokemonRequest.getEvAttack());
        setEvDefense(pokemonRequest.getEvDefense());
        setEvSpAttack(pokemonRequest.getEvSpAttack());
        setEvSpDefense(pokemonRequest.getEvSpDefense());
        setEvSpeed(pokemonRequest.getEvSpeed());
        setGender(pokemonRequest.getGender());
        setNature(pokemonRequest.getNature());
        setHeldItem(pokemonRequest.getHeldItem());
        setAbility(pokemonRequest.getAbility());
        setMove1(pokemonRequest.getMove1());
        setMove2(pokemonRequest.getMove2());
        setMove3(pokemonRequest.getMove3());
        setMove4(pokemonRequest.getMove4());
    }

    @Transient
    public PokemonRequest generateRequest() {
        return PokemonRequest.builder()
                .id(getId())
                .teamId(getTeam().getId())
                .position(getPosition())
                .name(getName())
                .level(getLevel())
                .ivHp(getIvHp())
                .ivAttack(getIvAttack())
                .ivDefense(getIvDefense())
                .ivSpAttack(getIvSpAttack())
                .ivSpDefense(getIvSpDefense())
                .ivSpeed(getIvSpeed())
                .evHp(getEvHp())
                .evAttack(getEvAttack())
                .evDefense(getEvDefense())
                .evSpAttack(getEvSpAttack())
                .evSpDefense(getEvSpDefense())
                .evSpeed(getEvSpeed())
                .gender(getGender())
                .nature(getNature())
                .heldItem(getHeldItem())
                .ability(getAbility())
                .move1(getMove1())
                .move2(getMove2())
                .move3(getMove3())
                .move4(getMove4())
                .build();
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", team=" + team.getId() +
                ", position=" + position +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", IvHp=" + IvHp +
                ", IvAttack=" + IvAttack +
                ", IvDefense=" + IvDefense +
                ", IvSpAttack=" + IvSpAttack +
                ", IvSpDefense=" + IvSpDefense +
                ", IvSpeed=" + IvSpeed +
                ", EvHp=" + EvHp +
                ", EvAttack=" + EvAttack +
                ", EvDefense=" + EvDefense +
                ", EvSpAttack=" + EvSpAttack +
                ", EvSpDefense=" + EvSpDefense +
                ", EvSpeed=" + EvSpeed +
                ", gender='" + gender + '\'' +
                ", nature='" + nature + '\'' +
                ", heldItem='" + heldItem + '\'' +
                ", ability='" + ability + '\'' +
                ", move1='" + move1 + '\'' +
                ", move2='" + move2 + '\'' +
                ", move3='" + move3 + '\'' +
                ", move4='" + move4 + '\'' +
                '}';
    }
}
