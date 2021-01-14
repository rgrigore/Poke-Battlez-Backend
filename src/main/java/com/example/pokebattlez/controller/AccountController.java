package com.example.pokebattlez.controller;

import com.example.pokebattlez.controller.dao.AccountDao;
import com.example.pokebattlez.model.dao.Account;
import com.example.pokebattlez.model.request.AccountConfirmation;
import com.example.pokebattlez.model.request.LoginForm;
import com.example.pokebattlez.model.request.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@MessageMapping("/account/*")
public class AccountController {

    private final AccountDao accountDao;
    private final SimpMessagingTemplate template;

    @Autowired
    public AccountController(AccountDao accountDao, SimpMessagingTemplate template) {
        this.accountDao = accountDao;
        this.template = template;
    }

    @MessageMapping("/register/{id}")
    public void register(RegisterForm form, @DestinationVariable int id) {
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

        sendConfirmation(confirmation, id);
    }

    @MessageMapping("/login/{id}")
    public void login(LoginForm form, @DestinationVariable int id) {
        Optional<Account> account = accountDao.get(form.getEmail());

        AccountConfirmation confirmation = new AccountConfirmation();

        account.ifPresentOrElse(acc -> {
            if (acc.getPassword().equals(form.getPassword())) {
                confirmation.setState(true);
                confirmation.setId(acc.getId());
            }
        }, () -> confirmation.setState(false));

        sendConfirmation(confirmation, id);
    }

    private void sendConfirmation(AccountConfirmation confirmation, int id) {
        template.convertAndSend(String.format("/account/confirm/%d", id), confirmation);
    }
}
