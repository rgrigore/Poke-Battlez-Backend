package com.example.pokebattlez.controller.service;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.model.entity.Account;
import com.example.pokebattlez.model.request.AccountConfirmation;
import com.example.pokebattlez.model.request.LoginForm;
import com.example.pokebattlez.model.request.RegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService {

    private final AccountRepository repository;

    public Account generateAccount(RegisterForm form) {
        return Account.builder()
                .email(form.getEmail())
                .username(form.getUsername())
                .password(form.getPassword())
                .build();
    }

    public AccountConfirmation registerAccount(Account account) {
        try {
            repository.save(account);

            return AccountConfirmation.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .state(true)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AccountConfirmation.builder().state(false).build();
    }

    public AccountConfirmation loginAccount(LoginForm form) {
        Optional<Account> accountOptional = repository.findAccountByEmail(form.getEmail());

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (account.getPassword().equals(form.getPassword())) {
                return AccountConfirmation.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .state(true)
                        .build();
            }
        }

        return AccountConfirmation.builder().state(false).build();
    }
}
