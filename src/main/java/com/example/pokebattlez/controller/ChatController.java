package com.example.pokebattlez.controller;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.controller.repository.PokemonRepository;
import com.example.pokebattlez.controller.repository.TeamRepository;
import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.entity.Team;
import com.example.pokebattlez.model.request.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ChatController {

    private final SimpMessagingTemplate template;
    private final OnlineUsers onlineUsers;
    private final AccountRepository accountRepository;
    private final TeamRepository teamRepository;
    private final PokemonRepository pokemonRepository;

    @MessageMapping("/message/lobby")
    @SendTo("/chat/lobby")
    public ChatMessage lobbyChat(SimpMessageHeaderAccessor sha, UserMessage userMessage) {
        return new ChatMessage(
            onlineUsers
                    .getUser(Objects.requireNonNull(sha.getUser()).getName())
                    .map(User::getName)
                    .orElse("Misingno"),
            HtmlUtils.htmlEscape(userMessage.getBody())
        );
    }

    @MessageMapping("/chat/private")
    public void privateChat(SimpMessageHeaderAccessor sha, PrivateMessageReceive pmr) {
        PrivateMessageSend pms = PrivateMessageSend.builder()
                .from(onlineUsers
                        .getUser(Objects.requireNonNull(sha.getUser()).getName())
                        .orElse(null)
                )
                .to(onlineUsers
                        .getUser(pmr.getTo())
                        .orElse(null)
                )
                .body(pmr.getBody())
                .sender(true)
                .build();

        template.convertAndSend(String.format("/chat/private/%s", sha.getUser().getName()), pms);
        pms.setSender(false);
        template.convertAndSend(String.format("/chat/private/%s", onlineUsers.getConId(pmr.getTo())), pms);
    }

    @MessageMapping("/chat/pokemon")
    public void updatePokemon(SimpMessageHeaderAccessor sha, PokemonRequest pokemonRequestReceive) {
        System.out.println(pokemonRequestReceive);
         pokemonRepository.findById(pokemonRequestReceive.getId())
            .ifPresentOrElse(
                pokemon -> {
                    pokemon.updateFields(pokemonRequestReceive);
                    pokemonRepository.save(pokemon);
                    System.out.println("found: " + pokemon);
                },
                () -> {
                    Team team = teamRepository
                            .findById(pokemonRequestReceive.getTeamId())
                            .orElse(Team.builder()
                                    .trainer(
                                            accountRepository
                                                    .findById(
                                                            Objects.requireNonNull(onlineUsers
                                                                    .getUser(Objects.requireNonNull(sha.getUser()).getName())
                                                                    .map(User::getId)
                                                                    .orElse(null))
                                                    )
                                                    .orElse(null)
                                    )
                                    .pokemon(new ArrayList<>())
                                    .build()
                            );
//                    System.out.println(team);
                    com.example.pokebattlez.model.entity.Pokemon test = new com.example.pokebattlez.model.entity.Pokemon(pokemonRequestReceive);
//                    System.out.println(test);
                    team.addPokemon(test);
                    teamRepository.save(team);
                }
            );
         sendTeamUpdate(Objects.requireNonNull(sha.getUser()).getName());
    }

    private void sendTeamUpdate(String connectionId) {
        template.convertAndSend(
            String.format("/chat/team/%s", connectionId),
            teamRepository.findTeamByTrainer_Id(onlineUsers
                    .getUser(connectionId)
                    .map(User::getId)
                    .orElse(null)
            ).map(Team::getPokemon).orElse(new ArrayList<>())
                    .stream().map(PokemonRequest::new)
                    .collect(Collectors.toList())
        );
    }
}
