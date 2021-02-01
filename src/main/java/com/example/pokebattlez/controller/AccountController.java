package com.example.pokebattlez.controller;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.model.entity.Account;
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

    private final AccountRepository accountRepository;
    private final SimpMessagingTemplate template;

    @Autowired
    public AccountController(AccountRepository accountRepository, SimpMessagingTemplate template) {
        this.accountRepository = accountRepository;
        this.template = template;
    }

    @MessageMapping("/register/{id}")
    public void register(RegisterForm form, @DestinationVariable int id) {

        Account account = Account.builder()
                .email(form.getEmail())
                .username(form.getUsername())
                .password(form.getPassword())
                .build();

        AccountConfirmation confirmation = new AccountConfirmation();

        try {
            accountRepository.save(account);
            confirmation.setState(true);
            confirmation.setId(account.getId());
            confirmation.setUsername(account.getUsername());
        } catch (Exception ignored) {
            confirmation.setState(false);
        }

        sendConfirmation(confirmation, id);
    }

    @MessageMapping("/login/{id}")
    public void login(LoginForm form, @DestinationVariable long id) {
        Optional<Account> account = accountRepository.findById(id);

        AccountConfirmation confirmation = new AccountConfirmation();

        account.ifPresentOrElse(acc -> {
            if (acc.getPassword().equals(form.getPassword())) {
                confirmation.setState(true);
                confirmation.setId(acc.getId());
                confirmation.setUsername(acc.getUsername());
            }
        }, () -> confirmation.setState(false));

        sendConfirmation(confirmation, id);
    }

    private void sendConfirmation(AccountConfirmation confirmation, long id) {
        template.convertAndSend(String.format("/account/confirm/%d", id), confirmation);
    }
}
