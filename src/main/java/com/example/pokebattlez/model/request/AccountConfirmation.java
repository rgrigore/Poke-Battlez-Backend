package com.example.pokebattlez.model.request;

public class AccountConfirmation {
    private boolean state;
    private long id;
    private String username;

    public AccountConfirmation(boolean state, int id, String username) {
        this.state = state;
        this.id = id;
        this.username = username;
    }

    public AccountConfirmation() {
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
