package com.example.pokebattlez.repository;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.model.entity.Account;
import com.example.pokebattlez.model.request.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    public void populateUsers() {
        for(int i = 1; i < 4; i++) {
            Account account= Account.builder()
                    .username("account" + i)
                    .email("account" + i + "@gmail.com")
                    .password("account" + i)
                    .build();
            accountRepository.save(account);
        }
    }

    @Test
    public void getAllUsers() {
        populateUsers();
        assertEquals(3, accountRepository.findAll().size());
        accountRepository.deleteAll();
    }

    @Test
    public void addNewUser() {
        Account toAdd= Account.builder()
                .username("account3")
                .email("account3@gmail.com")
                .password("account3")
                .build();
        accountRepository.save(toAdd);
        assertThat(accountRepository.findAll().size()).isEqualTo(1);
        accountRepository.deleteAll();
    }

    @Test
    public void deleteUser() {
        Account toAdd= Account.builder()
                .username("account3")
                .email("account3@gmail.com")
                .password("account3")
                .build();
        accountRepository.save(toAdd);
        assertThat(accountRepository.findAll().size()).isEqualTo(1);
        accountRepository.delete(toAdd);
        assertThat(accountRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void getUserByEmail() {
        populateUsers();
        Account expected = Account.builder()
                .username("account1")
                .email("account1@gmail.com")
                .password("account1")
                .build();
        String email = accountRepository.findAccountByEmail("account1@gmail.com").map(Account::getEmail).orElse(null);
        assertThat(email).isEqualTo(expected.getEmail());
        accountRepository.deleteAll();
    }

    @Test
    public void getUserByUsername() {
        populateUsers();
        Account expected = Account.builder()
                .username("account1")
                .email("account1@gmail.com")
                .password("account1")
                .build();
        String username = accountRepository.findAccountByUsername("account1").map(Account::getUsername).orElse(null);
        assertThat(username).isEqualTo(expected.getUsername());
        accountRepository.deleteAll();
    }

    @Test
    public void returnUserFromAccount() {
        populateUsers();
        User expected = User.builder().name("account3").build();
        User actual = accountRepository.findAccountByEmail("account3@gmail.com").map(account -> new User(account.generateUser().getId(), account.generateUser().getName())).orElse(null);
        assertThat(actual != null ? actual.getName() : null).isEqualTo(expected.getName());
        accountRepository.deleteAll();
    }
}
