package com.example.pokebattlez.model.request;

public class UserMessage {
    private int userId;
    private String body;

    public UserMessage(int userId, String body) {
        this.userId = userId;
        this.body = body;
    }

    public UserMessage() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
