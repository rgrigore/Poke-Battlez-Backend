package com.example.pokebattlez.controller;

import com.example.pokebattlez.controller.service.AuthenticationService;
import com.example.pokebattlez.model.entity.Account;
import com.example.pokebattlez.model.request.AccountConfirmation;
import com.example.pokebattlez.model.request.LoginForm;
import com.example.pokebattlez.model.request.RegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/*")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public AccountConfirmation register(@RequestBody RegisterForm form) {
        Account account = service.generateAccount(form);

        return service.registerAccount(account);
    }

    @PostMapping("/login")
    public AccountConfirmation login(@RequestBody LoginForm form) {
        return service.loginAccount(form);
    }
}
