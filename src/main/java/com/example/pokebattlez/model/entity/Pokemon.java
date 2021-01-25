package com.example.pokebattlez.model.entity;

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
}
