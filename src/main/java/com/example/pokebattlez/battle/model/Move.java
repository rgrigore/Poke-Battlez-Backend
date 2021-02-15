package com.example.pokebattlez.battle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Move {
    private String name;

    private int position;

    private int power;
    private int acc;
    private int pp;
    private Type type;
    private Category category;
}
