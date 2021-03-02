package com.example.pokebattlez.controller;

import com.example.pokebattlez.controller.service.TeamService;
import com.example.pokebattlez.model.request.PokemonRequest;
import com.example.pokebattlez.model.request.TeamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team/*")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamController {

    private final TeamService service;

    @PostMapping("/{id}/update")
    public TeamRequest updatePokemon(@PathVariable Long id, @RequestBody PokemonRequest pokemonRequest) {
        service.updatePokemon(
                id,
                pokemonRequest
        );

        return service.getTeam(id);
    }

    @GetMapping("/{id}")
    public TeamRequest getTeam(@PathVariable Long id) {
        return service.getTeam(id);
    }
}
