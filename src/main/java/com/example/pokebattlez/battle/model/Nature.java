package com.example.pokebattlez.battle.model;

import static com.example.pokebattlez.battle.model.Stat.*;

public enum Nature {
    Hardy(ATTACK, ATTACK), Lonely(ATTACK, DEFENCE), Adamant(ATTACK, SP_ATTACK), Naughty(ATTACK, SP_DEFENCE), Brave(ATTACK, SPEED),
    Bold(DEFENCE, ATTACK), Docile(DEFENCE, DEFENCE), Impish(DEFENCE, SP_ATTACK), Lax(DEFENCE, SP_DEFENCE), Relaxed(DEFENCE, SPEED),
    Modest(SP_ATTACK, ATTACK), Mild(SP_ATTACK, DEFENCE), Bashful(SP_ATTACK, SP_ATTACK), Rash(SP_ATTACK, SP_DEFENCE), Quiet(SP_ATTACK, SPEED),
    Calm(SP_DEFENCE, ATTACK), Gentle(SP_DEFENCE, DEFENCE), Careful(SP_DEFENCE, SP_ATTACK), Quirky(SP_DEFENCE, SP_DEFENCE), Sassy(SP_DEFENCE, SPEED),
    Timid(SPEED, ATTACK), Hasty(SPEED, DEFENCE), Jolly(SPEED, SP_ATTACK), Naive(SPEED, SP_DEFENCE), Serious(SPEED, SPEED);

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
