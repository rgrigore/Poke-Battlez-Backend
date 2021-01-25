package com.example.pokebattlez.controller.repository;

import com.example.pokebattlez.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
