package com.example.pokebattlez.controller;

import com.example.pokebattlez.controller.dao.AccountDao;
import com.example.pokebattlez.model.AccountConfirmation;
import com.example.pokebattlez.model.LoginForm;
import com.example.pokebattlez.model.RegisterForm;
import com.example.pokebattlez.model.dao.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@MessageMapping("/account/*")
public class AccountController {

    private final AccountDao accountDao;

    @Autowired
    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @MessageMapping("/register")
    @SendTo("/account/register-confirmation")
    public AccountConfirmation register(RegisterForm form) {
        Account account = new Account();
        account.setEmail(form.getEmail());
        account.setUsername(form.getUsername());
        account.setPassword(form.getPassword());

        AccountConfirmation confirmation = new AccountConfirmation();

        try {
            accountDao.add(account);
            confirmation.setState(true);
            confirmation.setId(account.getId());
        } catch (Exception ignored) {
            confirmation.setState(false);
        }

        return confirmation;
    }

    @MessageMapping("/login")
    @SendTo("/account/login-confirmation")
    public AccountConfirmation login(LoginForm form) {
        Optional<Account> account = accountDao.get(form.getEmail());

        AccountConfirmation confirmation = new AccountConfirmation();

        account.ifPresentOrElse(acc -> {
            if (acc.getPassword().equals(form.getPassword())) {
                confirmation.setState(true);
                confirmation.setId(acc.getId());
            }
        }, () -> confirmation.setState(false));

        return confirmation;
    }
}
