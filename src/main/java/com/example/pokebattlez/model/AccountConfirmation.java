package com.example.pokebattlez.model;

public class AccountConfirmation {
    private boolean state;
    private int id;

    public AccountConfirmation(boolean state, int id) {
        this.state = state;
        this.id = id;
    }

    public AccountConfirmation() {
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getSessionId() {
        return id;
    }

    public void setSessionId(int id) {
        this.id = id;
    }
}
