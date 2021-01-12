package com.example.pokebattlez.controller.dao;

import com.example.pokebattlez.model.dao.Account;

public interface AccountDao {
    Account get(int id);
    Account get(String email);
    void add(Account account);
    void update(Account oldAccount, Account newAccount);
}
