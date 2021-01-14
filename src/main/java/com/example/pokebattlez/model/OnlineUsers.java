package com.example.pokebattlez.model;

import com.example.pokebattlez.controller.dao.AccountDao;
import com.example.pokebattlez.model.request.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OnlineUsers {
    private static final List<User> users = new LinkedList<>();

    private static final Map<String, Integer> conIdMap = new HashMap<>();

    private boolean hasChanged = false;

    private final AccountDao accountDao;

    @Autowired
    public OnlineUsers(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void addUser(int id, String conId) {
        accountDao.get(id).ifPresent(account -> {
            User user = new User(account);
            users.add(user);
            conIdMap.put(conId, user.getId());
        });
        hasChanged = true;
    }

    public void removeUser(int id) {
        users.stream().filter(user -> user.getId() == id).findFirst().ifPresent(users::remove);
        hasChanged = true;
    }

    public void removeUser(String conId) {
        if (conIdMap.containsKey(conId)) {
            users.stream().filter(user -> user.getId() == conIdMap.get(conId)).findFirst().ifPresent(users::remove);
            conIdMap.remove(conId);
            hasChanged = true;
        }
    }

    public Optional<User> getUser(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean hasChanged() {
        boolean state = hasChanged;
        hasChanged = false;
        return state;
    }
}
