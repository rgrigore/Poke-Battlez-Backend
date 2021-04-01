package com.example.pokebattlez.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.model.entity.Account;
import com.example.pokebattlez.model.request.AccountConfirmation;
import com.example.pokebattlez.model.request.LoginForm;
import com.example.pokebattlez.model.request.RegisterForm;
import com.example.pokebattlez.security.JwtTokenService;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtTokenService.class, AuthenticationService.class})
@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private JwtTokenService jwtTokenService;

    @Test
    public void testGenerateAccountCase1() {
        // given
        Account account = new Account();
        account.setEmail("jane.doe@example.org");
        account.setPassword("iloveyou");
        account.setRoles(new ArrayList<String>());
        account.setUsername("janedoe");
        account.setId(123L);

        // when
        when(this.accountRepository.save((Account) any())).thenReturn(account);
        Account actualGenerateAccountResult = this.authenticationService
                .generateAccount(new RegisterForm("jane.doe@example.org", "janedoe", "iloveyou"));

        // then
        assertEquals("jane.doe@example.org", actualGenerateAccountResult.getEmail());
        assertEquals("janedoe", actualGenerateAccountResult.getUsername());
        assertNull(actualGenerateAccountResult.getId());
        verify(this.accountRepository).save((Account) any());
    }

    @Test
    public void testGenerateAccountCase2() {
        // given
        Account account = new Account(123L, "jane.doe@example.org", "janedoe", "iloveyou", new ArrayList<String>());
        account.setEmail("jane.doe@example.org");
        account.setPassword("iloveyou");
        account.setRoles(new ArrayList<String>());
        account.setUsername("janedoe");
        account.setId(123L);

        // when
        when(this.accountRepository.save((Account) any())).thenReturn(account);
        RegisterForm registerForm = mock(RegisterForm.class);
        when(registerForm.getPassword()).thenReturn("foo");
        when(registerForm.getUsername()).thenReturn("foo");
        when(registerForm.getEmail()).thenReturn("foo");
        Account actualGenerateAccountResult = this.authenticationService.generateAccount(registerForm);

        // then
        assertEquals("foo", actualGenerateAccountResult.getEmail());
        assertEquals("foo", actualGenerateAccountResult.getUsername());
        assertNull(actualGenerateAccountResult.getId());
        verify(this.accountRepository).save((Account) any());
        verify(registerForm).getPassword();
        verify(registerForm).getEmail();
        verify(registerForm).getUsername();
    }

    @Test
    public void testLoginAccountResponse() throws AuthenticationException {
        // given
        Account account = new Account();
        account.setEmail("jane.doe@example.org");
        account.setPassword("iloveyou");
        account.setRoles(new ArrayList<String>());
        account.setUsername("janedoe");
        account.setId(123L);

        // when
        when(this.jwtTokenService.createToken(anyString(), (java.util.List<String>) any())).thenReturn("foo");
        when(this.authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        Optional<Account> ofResult = Optional.<Account>of(account);
        when(this.accountRepository.findAccountByEmail(anyString())).thenReturn(ofResult);
        ResponseEntity<AccountConfirmation> actualLoginAccountResult = this.authenticationService
                .loginAccount(new LoginForm("jane.doe@example.org", "iloveyou"));

        // then
        assertEquals("<200 OK OK,AccountConfirmation(state=true, id=123, username=janedoe, email=jane.doe@example.org,"
                + " roles=[], token=foo),[]>", actualLoginAccountResult.toString());
        assertEquals(HttpStatus.OK, actualLoginAccountResult.getStatusCode());
        assertTrue(actualLoginAccountResult.hasBody());
        AccountConfirmation body = actualLoginAccountResult.getBody();
        assertEquals("foo", body.getToken());
        assertEquals(123L, body.getId());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals("AccountConfirmation(state=true, id=123, username=janedoe, email=jane.doe@example.org, roles=[],"
                + " token=foo)", body.toString());
        assertEquals("janedoe", body.getUsername());
        assertTrue(body.isState());
        verify(this.accountRepository).findAccountByEmail(anyString());
        verify(this.authenticationManager).authenticate((Authentication) any());
        verify(this.jwtTokenService).createToken(anyString(), (java.util.List<String>) any());
    }

    @Test
    public void testLoginAccountThrowsOnUserNotFoundExceptionCase1() throws AuthenticationException {
        // given
        Account account = new Account();
        account.setEmail("jane.doe@example.org");
        account.setPassword("iloveyou");
        account.setRoles(new ArrayList<String>());
        account.setUsername("janedoe");
        account.setId(123L);

        // when
        when(this.jwtTokenService.createToken(anyString(), (java.util.List<String>) any()))
                .thenThrow(new UsernameNotFoundException("Msg"));
        when(this.authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        Optional<Account> ofResult = Optional.<Account>of(account);
        when(this.accountRepository.findAccountByEmail(anyString())).thenReturn(ofResult);

        // then
        assertThrows(BadCredentialsException.class,
                () -> this.authenticationService.loginAccount(new LoginForm("jane.doe@example.org", "iloveyou")));
        verify(this.accountRepository).findAccountByEmail(anyString());
        verify(this.authenticationManager).authenticate((Authentication) any());
        verify(this.jwtTokenService).createToken(anyString(), (java.util.List<String>) any());
    }

    @Test
    public void testLoginAccountThrowsOnBadCredentialsException() throws AuthenticationException {
        // given
        LoginForm loginForm = mock(LoginForm.class);

        // when
        when(this.jwtTokenService.createToken(anyString(), (java.util.List<String>) any())).thenReturn("foo");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(this.accountRepository.findAccountByEmail(anyString())).thenReturn(Optional.<Account>empty());
        when(loginForm.getPassword()).thenThrow(new BadCredentialsException("Msg"));
        when(loginForm.getEmail()).thenReturn("foo");

        // then
        assertThrows(BadCredentialsException.class, () -> this.authenticationService.loginAccount(loginForm));
        verify(loginForm).getPassword();
        verify(loginForm).getEmail();
    }

    @Test
    public void testLoginAccountThrowsOnUserNotFoundExceptionCase2() throws AuthenticationException {
        // given
        LoginForm loginForm = mock(LoginForm.class);

        // when
        when(this.jwtTokenService.createToken(anyString(), (java.util.List<String>) any())).thenReturn("foo");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(this.accountRepository.findAccountByEmail(anyString())).thenReturn(Optional.<Account>empty());

        when(loginForm.getPassword()).thenThrow(new UsernameNotFoundException("Msg"));
        when(loginForm.getEmail()).thenReturn("foo");

        // then
        assertThrows(BadCredentialsException.class, () -> this.authenticationService.loginAccount(loginForm));
        verify(loginForm).getPassword();
        verify(loginForm).getEmail();
    }
}

