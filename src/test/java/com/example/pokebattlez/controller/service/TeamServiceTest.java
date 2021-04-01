package com.example.pokebattlez.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.controller.repository.PokemonRepository;
import com.example.pokebattlez.controller.repository.TeamRepository;
import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.entity.Account;
import com.example.pokebattlez.model.entity.Pokemon;
import com.example.pokebattlez.model.entity.Team;
import com.example.pokebattlez.model.request.PokemonDTO;
import com.example.pokebattlez.model.request.TeamRequest;
import com.example.pokebattlez.model.request.User;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TeamService.class, OnlineUsers.class})
@ExtendWith(SpringExtension.class)
public class TeamServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private OnlineUsers onlineUsers;

    @MockBean
    private PokemonRepository pokemonRepository;

    @MockBean
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    @Test
    public void testUpdatePokemon() {
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

        Pokemon pokemon = new Pokemon();
        pokemon.setPosition(1);
        pokemon.setEvDefense(1);
        pokemon.setTeam(team);
        pokemon.setEvAttack(1);
        pokemon.setEvSpAttack(1);
        pokemon.setMove2("Move2");
        pokemon.setIvDefense(1);
        pokemon.setGender("Gender");
        pokemon.setLevel(1);
        pokemon.setIvAttack(1);
        pokemon.setEvSpDefense(1);
        pokemon.setIvSpAttack(1);
        pokemon.setIvHp(1);
        pokemon.setEvHp(1);
        pokemon.setNature("Nature");
        pokemon.setName("Name");
        pokemon.setIvSpeed(1);
        pokemon.setMove4("Move4");
        pokemon.setEvSpeed(1);
        pokemon.setHeldItem("Held Item");
        pokemon.setMove1("Move1");
        pokemon.setAbility("Ability");
        pokemon.setMove3("Move3");
        pokemon.setId(123L);
        pokemon.setIvSpDefense(1);
        Optional<Pokemon> ofResult = Optional.<Pokemon>of(pokemon);

        Account account1 = new Account();
        account1.setEmail("jane.doe@example.org");
        account1.setPassword("iloveyou");
        account1.setRoles(new ArrayList<String>());
        account1.setUsername("janedoe");
        account1.setId(123L);

        Team team1 = new Team();
        team1.setId(123L);
        team1.setPokemon(new ArrayList<Pokemon>());
        team1.setTrainer(account1);

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setPosition(1);
        pokemon1.setEvDefense(1);
        pokemon1.setTeam(team1);
        pokemon1.setEvAttack(1);
        pokemon1.setEvSpAttack(1);
        pokemon1.setMove2("Move2");
        pokemon1.setIvDefense(1);
        pokemon1.setGender("Gender");
        pokemon1.setLevel(1);
        pokemon1.setIvAttack(1);
        pokemon1.setEvSpDefense(1);
        pokemon1.setIvSpAttack(1);
        pokemon1.setIvHp(1);
        pokemon1.setEvHp(1);
        pokemon1.setNature("Nature");
        pokemon1.setName("Name");
        pokemon1.setIvSpeed(1);
        pokemon1.setMove4("Move4");
        pokemon1.setEvSpeed(1);
        pokemon1.setHeldItem("Held Item");
        pokemon1.setMove1("Move1");
        pokemon1.setAbility("Ability");
        pokemon1.setMove3("Move3");
        pokemon1.setId(123L);
        pokemon1.setIvSpDefense(1);
        when(this.pokemonRepository.save((Pokemon) any())).thenReturn(pokemon1);
        when(this.pokemonRepository.findById((Long) any())).thenReturn(ofResult);
        this.teamService.updatePokemon(123L, new PokemonDTO());
        verify(this.pokemonRepository).save((Pokemon) any());
        verify(this.pokemonRepository).findById((Long) any());
    }

    @Test
    public void testGetTeam() {
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
        TeamRequest actualTeam = this.teamService.getTeam(123L);
        assertEquals("TeamRequest(teamId=123, pokemon=[])", actualTeam.toString());
        assertEquals(123L, actualTeam.getTeamId().longValue());
        verify(this.teamRepository).findTeamByTrainer_Id((Long) any());
    }

    @Test
    public void testGetTeam2() {
        when(this.teamRepository.findTeamByTrainer_Id((Long) any())).thenReturn(Optional.<Team>empty());
        assertNull(this.teamService.getTeam(123L));
        verify(this.teamRepository).findTeamByTrainer_Id((Long) any());
    }

    @Test
    public void testGetTeamByConnId() {
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
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(123L, "Name")));
        TeamRequest actualTeamByConnId = this.teamService.getTeamByConnId("42");
        assertEquals("TeamRequest(teamId=123, pokemon=[])", actualTeamByConnId.toString());
        assertEquals(123L, actualTeamByConnId.getTeamId().longValue());
        verify(this.onlineUsers).getUser(anyString());
        verify(this.teamRepository).findTeamByTrainer_Id((Long) any());
    }

    @Test
    public void testGetTeamByConnId2() {
        when(this.teamRepository.findTeamByTrainer_Id((Long) any())).thenReturn(Optional.<Team>empty());
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(123L, "Name")));
        assertNull(this.teamService.getTeamByConnId("42"));
        verify(this.onlineUsers).getUser(anyString());
        verify(this.teamRepository).findTeamByTrainer_Id((Long) any());
    }

    @Test
    public void testGetTeamByConnId3() {
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
        when(user.getId()).thenReturn(1L);
        Optional<User> ofResult1 = Optional.<User>of(user);
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult1);
        TeamRequest actualTeamByConnId = this.teamService.getTeamByConnId("42");
        assertEquals("TeamRequest(teamId=123, pokemon=[])", actualTeamByConnId.toString());
        assertEquals(123L, actualTeamByConnId.getTeamId().longValue());
        verify(this.onlineUsers).getUser(anyString());
        verify(this.teamRepository).findTeamByTrainer_Id((Long) any());
        verify(user).getId();
    }

    @Test
    public void testGetTeamByConnId4() {
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
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>empty());
        assertNull(this.teamService.getTeamByConnId("42"));
        verify(this.onlineUsers).getUser(anyString());
    }
}

