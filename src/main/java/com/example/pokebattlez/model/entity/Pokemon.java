package com.example.pokebattlez.model.entity;

import com.example.pokebattlez.model.request.PokemonDTO;
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

    private Pokemon(PokemonDTO pokemonDTO) {
        updateFields(pokemonDTO);
    }

    public static Pokemon from(PokemonDTO pokemonDTO) {
        return new Pokemon(pokemonDTO);
    }

    @Transient
    public void updateFields(PokemonDTO pokemonDTO) {
        setPosition(pokemonDTO.getPosition());
        setName(pokemonDTO.getName());
        setLevel(pokemonDTO.getLevel());
        setIvHp(pokemonDTO.getIvHp());
        setIvAttack(pokemonDTO.getIvAttack());
        setIvDefense(pokemonDTO.getIvDefense());
        setIvSpAttack(pokemonDTO.getIvSpAttack());
        setIvSpDefense(pokemonDTO.getIvSpDefense());
        setIvSpeed(pokemonDTO.getIvSpeed());
        setEvHp(pokemonDTO.getEvHp());
        setEvAttack(pokemonDTO.getEvAttack());
        setEvDefense(pokemonDTO.getEvDefense());
        setEvSpAttack(pokemonDTO.getEvSpAttack());
        setEvSpDefense(pokemonDTO.getEvSpDefense());
        setEvSpeed(pokemonDTO.getEvSpeed());
        setGender(pokemonDTO.getGender());
        setNature(pokemonDTO.getNature());
        setHeldItem(pokemonDTO.getHeldItem());
        setAbility(pokemonDTO.getAbility());
        setMove1(pokemonDTO.getMove1());
        setMove2(pokemonDTO.getMove2());
        setMove3(pokemonDTO.getMove3());
        setMove4(pokemonDTO.getMove4());
    }

    @Transient
    public PokemonDTO generateRequest() {
        return PokemonDTO.builder()
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
