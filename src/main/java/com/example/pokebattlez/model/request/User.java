package com.example.pokebattlez.model.request;

import com.example.pokebattlez.model.dao.Account;

public class User {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(Account account) {
        this.id = account.getId();
        this.name = account.getUsername();
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("User [id: %d, name: %s]", getId(), getName());
    }
}
