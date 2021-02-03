package com.example.pokebattlez.controller.repository;

import com.example.pokebattlez.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findTeamByTrainer_Id(Long trainerId);
}
