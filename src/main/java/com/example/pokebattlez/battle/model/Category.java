package com.example.pokebattlez.battle.model;

public enum Category {
    PHYSICAL(Stat.ATTACK, Stat.DEFENSE),
    SPECIAL(Stat.SPECIAL_ATTACK, Stat.SPECIAL_DEFENSE),
    STATUS(null, null);

    private final Stat attack;
    private final Stat defense;

    Category(Stat attack, Stat defense) {
        this.attack = attack;
        this.defense = defense;
    }

    public Stat getAttack() {
        return attack;
    }

    public Stat getDefense() {
        return defense;
    }
}
