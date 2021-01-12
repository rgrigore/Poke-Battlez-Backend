package com.example.pokebattlez.controller.dao.memory;

import com.example.pokebattlez.controller.dao.AccountDao;
import com.example.pokebattlez.model.dao.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AccountDaoMem implements AccountDao {
    private static final List<Account> accounts = new ArrayList<>();

    @Override
    public Optional<Account> get(int id) {
        return accounts.stream().filter(account -> account.getId() == id).findFirst();
    }

    @Override
    public Optional<Account> get(String email) {
        return accounts.stream().filter(account -> account.getEmail().equals(email)).findFirst();
    }

    @Override
    public void add(Account account) {
        int position = accounts.size() + 1;
        accounts.add(account);

        if (accounts.size() != position) {
            position = accounts.indexOf(account);
        }

        account.setId(position);
    }

    @Override
    public void update(Account oldAccount, Account newAccount) {

    }
}
