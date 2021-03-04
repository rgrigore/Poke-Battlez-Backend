package com.example.pokebattlez.controller.service;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.model.entity.Account;
import com.example.pokebattlez.model.request.AccountConfirmation;
import com.example.pokebattlez.model.request.LoginForm;
import com.example.pokebattlez.model.request.RegisterForm;
import com.example.pokebattlez.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService {

    private final AccountRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public Account generateAccount(RegisterForm form) {
        Account newAccount = Account.builder()
                .email(form.getEmail())
                .username(form.getUsername())
                .password(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt(12)))
                .roles(Arrays.asList("ROLE_USER"))
                .build();
        repository.save(newAccount);
        return newAccount;
    }

    public ResponseEntity<AccountConfirmation> loginAccount(LoginForm form) {
        AccountConfirmation response = AccountConfirmation.builder().state(false).build();

        try {
            String email = form.getEmail();
            String psw = form.getPassword();

            Optional<Account> accountOptional = repository.findAccountByEmail(form.getEmail());
            Account account = Account.builder().build();
            if (accountOptional.isPresent()) {
                account = accountOptional.get();
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account.getUsername(), psw)
            );

            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenService.createToken(account.getUsername(), roles);

            response = AccountConfirmation.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .email(email)
                        .roles(roles)
                        .token(token)
                        .state(true)
                        .build();

        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }

        return ResponseEntity.ok(response);
    }
}
