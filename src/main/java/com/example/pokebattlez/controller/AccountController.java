package com.example.pokebattlez.controller;

import com.example.pokebattlez.model.AccountConfirmation;
import com.example.pokebattlez.model.LoginForm;
import com.example.pokebattlez.model.RegisterForm;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/account/*")
public class AccountController {

    @MessageMapping("/register")
    @SendTo("/account/register-confirmation")
    public AccountConfirmation register(RegisterForm form) {
        return new AccountConfirmation(false, null);
    }

    @MessageMapping("/login")
    @SendTo("/account/login-confirmation")
    public AccountConfirmation login(LoginForm form) {
        return new AccountConfirmation(false, null);
    }
}
