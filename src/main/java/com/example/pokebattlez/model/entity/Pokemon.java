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

    @Column(nullable = false) private Integer hpIV;
    @Column(nullable = false) private Integer attackIV;
    @Column(nullable = false) private Integer defenceIV;
    @Column(nullable = false) private Integer spAttackIV;
    @Column(nullable = false) private Integer spDefenceIV;
    @Column(nullable = false) private Integer speedIV;

    @Column(nullable = false) private Integer hpEV;
    @Column(nullable = false) private Integer attackEV;
    @Column(nullable = false) private Integer defenceEV;
    @Column(nullable = false) private Integer spAttackEV;
    @Column(nullable = false) private Integer spDefenceEV;
    @Column(nullable = false) private Integer speedEV;

    @Column(nullable = false) private String gender;
    @Column(nullable = false) private String nature;
    @Column(nullable = false) private String heldItem;
    @Column(nullable = false) private String ability;

    @Column(nullable = false) private String move1;
    @Column(nullable = false) private String move2;
    @Column(nullable = false) private String move3;
    @Column(nullable = false) private String move4;
}
