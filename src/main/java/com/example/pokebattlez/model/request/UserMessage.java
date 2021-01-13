package com.example.pokebattlez.model.request;

public class UserMessage {
    private String body;

    public UserMessage(String body) {
        this.body = body;
    }

    public UserMessage() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
