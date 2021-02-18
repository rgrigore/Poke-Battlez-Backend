package com.example.pokebattlez.battle.model;

public enum Category {
    PHYSICAL(Stat.ATTACK, Stat.DEFENCE),
    SPECIAL(Stat.SPECIAL_ATTACK, Stat.SPECIAL_DEFENCE),
    STATUS(null, null);

    private final Stat attack;
    private final Stat defence;

    Category(Stat attack, Stat defence) {
        this.attack = attack;
        this.defence = defence;
    }

    public Stat getAttack() {
        return attack;
    }

    public Stat getDefence() {
        return defence;
    }
}
