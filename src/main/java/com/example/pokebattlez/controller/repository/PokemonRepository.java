package com.example.pokebattlez.controller.repository;

import com.example.pokebattlez.model.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}
