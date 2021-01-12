package com.example.pokebattlez.controller.dao;

import com.example.pokebattlez.model.dao.Account;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> get(int id);
    Optional<Account> get(String email);
    void add(Account account);
    void update(Account oldAccount, Account newAccount);
}
