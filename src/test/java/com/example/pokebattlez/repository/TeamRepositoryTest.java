package com.example.pokebattlez.repository;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.controller.repository.PokemonRepository;
import com.example.pokebattlez.controller.repository.TeamRepository;
import com.example.pokebattlez.model.entity.Account;
import com.example.pokebattlez.model.entity.Pokemon;
import com.example.pokebattlez.model.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    public void addTestTeam() {
        Account user = Account.builder()
                .username("account3")
                .email("account3@gmail.com")
                .password("account3")
                .build();
        accountRepository.save(user);

        for (int i = 1; i < 5; i++) {
            Pokemon pokemon = Pokemon.builder()
                    .position(i)
                    .name("pokemon" + i)
                    .level(i)
                    .IvAttack(i)
                    .IvDefense(i)
                    .IvHp(i)
                    .IvSpAttack(i)
                    .IvSpDefense(i)
                    .IvSpeed(i)
                    .EvAttack(i)
                    .EvDefense(i)
                    .EvHp(i)
                    .EvSpAttack(i)
                    .EvSpDefense(i)
                    .EvSpeed(i)
                    .ability("pokemon" + i)
                    .gender("gender" + i)
                    .nature("nature" + i)
                    .heldItem("item" + i)
                    .move1("move1")
                    .move2("move2")
                    .move3("move3")
                    .move4("move4")
                    .build();
            pokemonRepository.save(pokemon);
        }
        Team team = Team.builder().trainer(user).pokemon(pokemonRepository.findAll()).build();
        teamRepository.save(team);
    }

    @Test
    public void addTeam() {
        addTestTeam();
        assertEquals(1, teamRepository.findAll().size());
        teamRepository.deleteAll();
    }

    @Test
    public void deleteTeam() {
        addTestTeam();
        assertEquals(1, teamRepository.findAll().size());
        teamRepository.deleteAll();
        assertEquals(0, teamRepository.findAll().size());
    }

    @Test
    public void getTeamByTrainerId() {
        addTestTeam();
        Long userId = accountRepository.findAccountByEmail("account3@gmail.com").map(Account::getId).orElse(null);
        Team expected = teamRepository.findAll().get(0);

        assertThat(teamRepository.findTeamByTrainer_Id(userId)).isEqualTo(Optional.of(expected));
    }

    @Test
    public void trainerShouldNotBeNull() {
        Team nullTrainerTeam = Team.builder().trainer(null).build();
        assertThrows(DataIntegrityViolationException.class, () -> teamRepository.save(nullTrainerTeam));
    }

    @Test
    public void addNonExistingTrainerToTeam() {
        Account nonExistingUser = Account.builder()
                .username("account3")
                .email("account3@gmail.com")
                .password("account3")
                .build();
        Team nonExitingTrainerTeam = Team.builder().trainer(nonExistingUser).build();
        assertThrows(InvalidDataAccessApiUsageException.class, () -> teamRepository.save(nonExitingTrainerTeam));
    }
}
