package com.example.pokebattlez.model;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.model.request.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OnlineUsers {
    private static final List<User> users = new LinkedList<>();
    private static final Map<String, Long> conIdMap = new HashMap<>();

    // TEST FOR PULL REQUEST

    private boolean hasChanged = false;

    private final AccountRepository accountRepository;

    @Autowired
    public OnlineUsers(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void addUser(long id, String conId) {
        accountRepository.findById(id).ifPresent(account -> {
            User user = account.generateUser();
            users.add(user);
            conIdMap.put(conId, user.getId());
        });
        hasChanged = true;
    }

    public void removeUser(long id) {
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

    public Optional<User> getUser(long id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    public Optional<User> getUser(String conId) {
        return getUser(conIdMap.get(conId));
    }

    public Optional<String> getConId(long id) {
        return conIdMap.entrySet().stream().filter(entry -> entry.getValue() == id).findFirst().map(Map.Entry::getKey);
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
