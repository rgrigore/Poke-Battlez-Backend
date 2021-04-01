package com.example.pokebattlez.controller.service;

import com.example.pokebattlez.battle.service.BattleService;
import com.example.pokebattlez.controller.repository.TeamRepository;
import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.entity.Account;
import com.example.pokebattlez.model.entity.Pokemon;
import com.example.pokebattlez.model.entity.Team;
import com.example.pokebattlez.model.request.ChallengeReceive;
import com.example.pokebattlez.model.request.ChallengeResponse;
import com.example.pokebattlez.model.request.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ChallengeService.class})
@ExtendWith(SpringExtension.class)
public class ChallengeServiceTest {
    @MockBean
    private BattleService battleService;

    @Autowired
    private ChallengeService challengeService;

    @MockBean
    private OnlineUsers onlineUsers;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @MockBean
    private TeamRepository teamRepository;

    @Test
    public void testRegister() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        this.challengeService.register(3L, 1L, 1L, 1L);
    }

    @Test
    public void testHasActiveChallenge() {
        assertFalse(this.challengeService.hasActiveChallenge(1L));
    }

    @Test
    public void testSendChallenge() {
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(123L, "Name")));
        this.challengeService.sendChallenge("Sender", new ChallengeReceive(1L));
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testSendChallenge2() {
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(5L, "Name")));
        this.challengeService.sendChallenge("Sender", new ChallengeReceive(1L));
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testSendChallenge3() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult);
        this.challengeService.sendChallenge("Sender", new ChallengeReceive(1L));
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testSendChallenge4() {
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>empty());
        this.challengeService.sendChallenge("Sender", new ChallengeReceive(1L));
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testRespond() {
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>empty());
        this.challengeService.respond("Responder", new ChallengeResponse());
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testRespond10() {
        Account account = new Account();
        account.setEmail("jane.doe@example.org");
        account.setPassword("iloveyou");
        account.setRoles(new ArrayList<String>());
        account.setUsername("janedoe");
        account.setId(123L);

        Team team = new Team();
        team.setId(123L);
        team.setPokemon(new ArrayList<Pokemon>());
        team.setTrainer(account);
        Optional<Team> ofResult = Optional.<Team>of(team);
        when(this.teamRepository.findTeamByTrainer_Id((Long) any())).thenReturn(ofResult);
        User user = mock(User.class);
        when(user.getName()).thenReturn("foo");
        when(user.getId()).thenReturn(1L);
        Optional<User> ofResult1 = Optional.<User>of(user);
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult1);
        when(this.battleService.generateBattle((Long) any(), (Long[]) any())).thenReturn("foo");
        ChallengeResponse challengeResponse = mock(ChallengeResponse.class);
        when(challengeResponse.getFrom()).thenReturn(Long.MIN_VALUE);
        when(challengeResponse.getAccept()).thenReturn(true);
        this.challengeService.respond("Responder", challengeResponse);
        verify(challengeResponse).getFrom();
        verify(challengeResponse).getAccept();
        verify(this.onlineUsers).getUser(anyString());
        verify(user).getId();
    }

    @Test
    public void testRespond11() {
        Account account = new Account();
        account.setEmail("jane.doe@example.org");
        account.setPassword("iloveyou");
        account.setRoles(new ArrayList<String>());
        account.setUsername("janedoe");
        account.setId(123L);

        Team team = new Team();
        team.setId(123L);
        team.setPokemon(new ArrayList<Pokemon>());
        team.setTrainer(account);
        Optional<Team> ofResult = Optional.<Team>of(team);
        when(this.teamRepository.findTeamByTrainer_Id((Long) any())).thenReturn(ofResult);
        User user = mock(User.class);
        when(user.getName()).thenReturn("foo");
        when(user.getId()).thenReturn(1L);
        Optional<User> ofResult1 = Optional.<User>of(user);
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult1);
        when(this.battleService.generateBattle((Long) any(), (Long[]) any())).thenReturn("foo");
        ChallengeResponse challengeResponse = mock(ChallengeResponse.class);
        when(challengeResponse.getFrom()).thenReturn(Long.MAX_VALUE);
        when(challengeResponse.getAccept()).thenReturn(false);
        this.challengeService.respond("Responder", challengeResponse);
        verify(challengeResponse).getFrom();
        verify(challengeResponse).getAccept();
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testRespond2() {
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(123L, "Name")));

        ChallengeResponse challengeResponse = new ChallengeResponse();
        challengeResponse.setAccept(true);
        this.challengeService.respond("Responder", challengeResponse);
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testRespond3() {
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(123L, "Name")));
        this.challengeService.respond("Responder", new ChallengeResponse(true, 1L));
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testRespond4() {
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(123L, "Name")));
        ChallengeResponse challengeResponse = mock(ChallengeResponse.class);
        when(challengeResponse.getFrom()).thenReturn(1L);
        when(challengeResponse.getAccept()).thenReturn(true);
        this.challengeService.respond("Responder", challengeResponse);
        verify(challengeResponse).getFrom();
        verify(challengeResponse).getAccept();
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testRespond5() {
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(1L, "Name")));
        ChallengeResponse challengeResponse = mock(ChallengeResponse.class);
        when(challengeResponse.getFrom()).thenReturn(1L);
        when(challengeResponse.getAccept()).thenReturn(true);
        this.challengeService.respond("Responder", challengeResponse);
        verify(challengeResponse).getFrom();
        verify(challengeResponse).getAccept();
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testRespond6() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult);
        ChallengeResponse challengeResponse = mock(ChallengeResponse.class);
        when(challengeResponse.getFrom()).thenReturn(1L);
        when(challengeResponse.getAccept()).thenReturn(true);
        this.challengeService.respond("Responder", challengeResponse);
        verify(challengeResponse).getFrom();
        verify(challengeResponse).getAccept();
        verify(this.onlineUsers).getUser(anyString());
        verify(user).getId();
    }

    @Test
    public void testRespond7() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult);
        when(this.battleService.generateBattle((Long) any(), (Long[]) any())).thenReturn("foo");
        ChallengeResponse challengeResponse = mock(ChallengeResponse.class);
        when(challengeResponse.getFrom()).thenReturn(0L);
        when(challengeResponse.getAccept()).thenReturn(true);
        this.challengeService.respond("Responder", challengeResponse);
        verify(challengeResponse).getFrom();
        verify(challengeResponse).getAccept();
        verify(this.onlineUsers).getUser(anyString());
        verify(user).getId();
    }

    @Test
    public void testRespond8() {
        User user = mock(User.class);
        when(user.getName()).thenReturn("foo");
        when(user.getId()).thenReturn(1L);
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult);
        when(this.battleService.generateBattle((Long) any(), (Long[]) any())).thenReturn("foo");
        ChallengeResponse challengeResponse = mock(ChallengeResponse.class);
        when(challengeResponse.getFrom()).thenReturn(-1L);
        when(challengeResponse.getAccept()).thenReturn(true);
        this.challengeService.respond("Responder", challengeResponse);
        verify(challengeResponse).getFrom();
        verify(challengeResponse).getAccept();
        verify(this.onlineUsers).getUser(anyString());
        verify(user).getId();
    }

    @Test
    public void testRespond9() {
        Account account = new Account();
        account.setEmail("jane.doe@example.org");
        account.setPassword("iloveyou");
        account.setRoles(new ArrayList<String>());
        account.setUsername("janedoe");
        account.setId(123L);

        Team team = new Team();
        team.setId(123L);
        team.setPokemon(new ArrayList<Pokemon>());
        team.setTrainer(account);
        Optional<Team> ofResult = Optional.<Team>of(team);
        when(this.teamRepository.findTeamByTrainer_Id((Long) any())).thenReturn(ofResult);
        User user = mock(User.class);
        when(user.getName()).thenReturn("foo");
        when(user.getId()).thenReturn(1L);
        Optional<User> ofResult1 = Optional.<User>of(user);
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult1);
        when(this.battleService.generateBattle((Long) any(), (Long[]) any())).thenReturn("foo");
        ChallengeResponse challengeResponse = mock(ChallengeResponse.class);
        when(challengeResponse.getFrom()).thenReturn(Long.MAX_VALUE);
        when(challengeResponse.getAccept()).thenReturn(true);
        this.challengeService.respond("Responder", challengeResponse);
        verify(challengeResponse).getFrom();
        verify(challengeResponse).getAccept();
        verify(this.onlineUsers).getUser(anyString());
        verify(user).getId();
    }
}

