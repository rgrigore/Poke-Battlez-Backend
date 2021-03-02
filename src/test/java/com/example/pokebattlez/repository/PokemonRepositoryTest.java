package com.example.pokebattlez.repository;

import com.example.pokebattlez.controller.repository.PokemonRepository;
import com.example.pokebattlez.model.entity.Pokemon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    public void populatePokemons() {
        for(int i = 1; i < 5; i++) {
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
    }

    @Test
    public void addNewPokemon() {
        Pokemon added = Pokemon.builder()
                .position(1)
                .name("pokemon" + 1)
                .level(1)
                .IvAttack(1)
                .IvDefense(1)
                .IvHp(1)
                .IvSpAttack(1)
                .IvSpDefense(1)
                .IvSpeed(1)
                .EvAttack(1)
                .EvDefense(1)
                .EvHp(1)
                .EvSpAttack(1)
                .EvSpDefense(1)
                .EvSpeed(1)
                .ability("pokemon" + 1)
                .gender("gender" + 1)
                .nature("nature" + 1)
                .heldItem("item" + 1)
                .move1("move1")
                .move2("move2")
                .move3("move3")
                .move4("move4")
                .build();
        pokemonRepository.save(added);
        assertThat(pokemonRepository.findAll().size()).isEqualTo(1);
        pokemonRepository.deleteAll();
    }

    @Test
    public void addMultiplePokemons() {
        populatePokemons();
        assertEquals(4, pokemonRepository.findAll().size());
        pokemonRepository.deleteAll();
    }

    @Test
    public void deleteOnePokemon() {
        Pokemon toDelete = Pokemon.builder()
                .position(1)
                .name("pokemon" + 1)
                .level(1)
                .IvAttack(1)
                .IvDefense(1)
                .IvHp(1)
                .IvSpAttack(1)
                .IvSpDefense(1)
                .IvSpeed(1)
                .EvAttack(1)
                .EvDefense(1)
                .EvHp(1)
                .EvSpAttack(1)
                .EvSpDefense(1)
                .EvSpeed(1)
                .ability("pokemon" + 1)
                .gender("gender" + 1)
                .nature("nature" + 1)
                .heldItem("item" + 1)
                .move1("move1")
                .move2("move2")
                .move3("move3")
                .move4("move4")
                .build();
        pokemonRepository.save(toDelete);
        assertEquals(1, pokemonRepository.findAll().size());
        pokemonRepository.delete(toDelete);
        assertEquals(0, pokemonRepository.findAll().size());
    }

    @Test
    public void deleteAll() {
        populatePokemons();
        assertEquals(4, pokemonRepository.findAll().size());
        pokemonRepository.deleteAll();
        assertEquals(0, pokemonRepository.findAll().size());
    }

    @Test
    public void findPokemonById() {
        Pokemon toFind = Pokemon.builder()
                .position(1)
                .name("pokemon" + 1)
                .level(1)
                .IvAttack(1)
                .IvDefense(1)
                .IvHp(1)
                .IvSpAttack(1)
                .IvSpDefense(1)
                .IvSpeed(1)
                .EvAttack(1)
                .EvDefense(1)
                .EvHp(1)
                .EvSpAttack(1)
                .EvSpDefense(1)
                .EvSpeed(1)
                .ability("pokemon" + 1)
                .gender("gender" + 1)
                .nature("nature" + 1)
                .heldItem("item" + 1)
                .move1("move1")
                .move2("move2")
                .move3("move3")
                .move4("move4")
                .build();
        pokemonRepository.save(toFind);
        pokemonRepository.flush();
        Long id = toFind.getId();
        System.out.println(id);
        assertThat(pokemonRepository.findById(id)).isEqualTo(Optional.of(toFind));
        pokemonRepository.deleteAll();
    }

    @Test
    public void fieldsShouldNotBeNull () {
        Pokemon nullPokemon = Pokemon.builder().name("null Pokemon").build();
        assertThrows(DataIntegrityViolationException.class, () -> pokemonRepository.save(nullPokemon));
    }
}
