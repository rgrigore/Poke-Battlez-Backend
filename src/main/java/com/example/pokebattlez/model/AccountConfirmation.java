package com.example.pokebattlez.model;

public class AccountConfirmation {
    private boolean state;
    private String sessionId;

    public AccountConfirmation(boolean state, String sessionId) {
        this.state = state;
        this.sessionId = sessionId;
    }

    public AccountConfirmation() {
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
