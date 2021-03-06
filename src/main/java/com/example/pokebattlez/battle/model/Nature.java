package com.example.pokebattlez.battle.model;

import static com.example.pokebattlez.battle.model.Stat.*;

public enum Nature {
    HARDY(ATTACK, ATTACK), LONELY(ATTACK, DEFENSE), ADAMANT(ATTACK, SPECIAL_ATTACK), NAUGHTY(ATTACK, SPECIAL_DEFENSE), BRAVE(ATTACK, SPEED),
    BOLD(DEFENSE, ATTACK), DOCILE(DEFENSE, DEFENSE), IMPISH(DEFENSE, SPECIAL_ATTACK), LAX(DEFENSE, SPECIAL_DEFENSE), RELAXED(DEFENSE, SPEED),
    MODEST(SPECIAL_ATTACK, ATTACK), MILD(SPECIAL_ATTACK, DEFENSE), BASHFUL(SPECIAL_ATTACK, SPECIAL_ATTACK), RASH(SPECIAL_ATTACK, SPECIAL_DEFENSE), QUIET(SPECIAL_ATTACK, SPEED),
    CALM(SPECIAL_DEFENSE, ATTACK), GENTLE(SPECIAL_DEFENSE, DEFENSE), CAREFUL(SPECIAL_DEFENSE, SPECIAL_ATTACK), QUIRKY(SPECIAL_DEFENSE, SPECIAL_DEFENSE), SASSY(SPECIAL_DEFENSE, SPEED),
    TIMID(SPEED, ATTACK), HASTY(SPEED, DEFENSE), JOLLY(SPEED, SPECIAL_ATTACK), NAIVE(SPEED, SPECIAL_DEFENSE), SERIOUS(SPEED, SPEED);

    private final Stat up;
    private final Stat down;

    Nature(Stat up, Stat down) {
        this.up = up;
        this.down = down;
    }

    public Stat getUp() {
        return up;
    }

    public Stat getDown() {
        return down;
    }
}
