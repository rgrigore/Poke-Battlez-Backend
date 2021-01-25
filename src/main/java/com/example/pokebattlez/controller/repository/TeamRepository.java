package com.example.pokebattlez.controller.repository;

import com.example.pokebattlez.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
