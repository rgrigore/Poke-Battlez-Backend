package com.example.pokebattlez.model;

import com.example.pokebattlez.controller.dao.AccountDao;
import com.example.pokebattlez.model.request.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class OnlineUsers {
    private static final List<User> users = new LinkedList<>();

    private final AccountDao accountDao;

    @Autowired
    public OnlineUsers(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void addUser(int id) {
        accountDao.get(id).ifPresent(account -> users.add(new User(account)));
    }

    public List<User> getUsers() {
        return users;
    }
}
