package com.example.pokebattlez.model.entity;

import com.example.pokebattlez.model.request.PokemonRequest;
import lombok.*;

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
    @Column(nullable = false) private Integer IvDefence;
    @Column(nullable = false) private Integer IvSpAttack;
    @Column(nullable = false) private Integer IvSpDefence;
    @Column(nullable = false) private Integer IvSpeed;

    @Column(nullable = false) private Integer EvHp;
    @Column(nullable = false) private Integer EvAttack;
    @Column(nullable = false) private Integer EvDefence;
    @Column(nullable = false) private Integer EvSpAttack;
    @Column(nullable = false) private Integer EvSpDefence;
    @Column(nullable = false) private Integer EvSpeed;

    @Column(nullable = false) private String gender;
    @Column(nullable = false) private String nature;
    @Column(nullable = false) private String heldItem;
    @Column(nullable = false) private String ability;

    @Column(nullable = false) private String move1;
    @Column(nullable = false) private String move2;
    @Column(nullable = false) private String move3;
    @Column(nullable = false) private String move4;

    public Pokemon(PokemonRequest pokemonRequest) {
        updateFields(pokemonRequest);
    }

    @Transient
    public void updateFields(PokemonRequest pokemonRequest) {
        setPosition(pokemonRequest.getPosition());
        setName(pokemonRequest.getName());
        setLevel(pokemonRequest.getLevel());
        setIvHp(pokemonRequest.getIvHp());
        setIvAttack(pokemonRequest.getIvAttack());
        setIvDefence(pokemonRequest.getIvDefence());
        setIvSpAttack(pokemonRequest.getIvSpAttack());
        setIvSpDefence(pokemonRequest.getIvSpDefence());
        setIvSpeed(pokemonRequest.getIvSpeed());
        setEvHp(pokemonRequest.getEvHp());
        setEvAttack(pokemonRequest.getEvAttack());
        setEvDefence(pokemonRequest.getEvDefence());
        setEvSpAttack(pokemonRequest.getEvSpAttack());
        setEvSpDefence(pokemonRequest.getEvSpDefence());
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
                .ivDefence(getIvDefence())
                .ivSpAttack(getIvSpAttack())
                .ivSpDefence(getIvSpDefence())
                .ivSpeed(getIvSpeed())
                .evHp(getEvHp())
                .evAttack(getEvAttack())
                .evDefence(getEvDefence())
                .evSpAttack(getEvSpAttack())
                .evSpDefence(getEvSpDefence())
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
}
