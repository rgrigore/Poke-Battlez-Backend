package com.example.pokebattlez.security;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.model.entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomUserCredentialsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account: " + username + "not found!"));

        return new User(account.getUsername(), account.getPassword(),
                            account.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }


}
