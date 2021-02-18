package com.example.pokebattlez.controller;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.controller.repository.PokemonRepository;
import com.example.pokebattlez.controller.repository.TeamRepository;
import com.example.pokebattlez.controller.service.ChallengeService;
import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.entity.Pokemon;
import com.example.pokebattlez.model.entity.Team;
import com.example.pokebattlez.model.request.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
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
    private final ChallengeService challengeService;

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
        template.convertAndSend(String.format("/chat/private/%s", onlineUsers.getConId(pmr.getTo()).orElse(null)), pms);
    }

    @MessageMapping("/chat/challenge")
    public void challenge(SimpMessageHeaderAccessor sha, ChallengeReceive chr) {

        onlineUsers.getUser(Objects.requireNonNull(sha.getUser()).getName())
                .ifPresent(user -> {
                    if (!challengeService.hasActiveChallenge(user.getId())) {
                        challengeService.register(user.getId(), chr.getTo());
                        ChallengeSend chs = ChallengeSend.builder()
                                .from(user)
                                .build();
                        template.convertAndSend(
                                String.format("/chat/challenge/%s",
                                        onlineUsers.getConId(chr.getTo()).orElse(null)),
                                chs
                        );
                    }
                });
    }

    @MessageMapping("/chat/challenge/accept")
    public void acceptChallenge(SimpMessageHeaderAccessor sha, @RequestParam("accept") boolean accept, @RequestParam("from") Long from) {
        onlineUsers.getUser(Objects.requireNonNull(sha.getUser()).getName())
                .ifPresent(user -> {
                    if (accept) {
                        challengeService.accept(user.getId(), from);
                    } else {
                        challengeService.decline(from);
                    }
                });
    }

    @MessageMapping("/chat/pokemon")
    public void updatePokemon(SimpMessageHeaderAccessor sha, PokemonRequest pokemonRequestReceive) {
         pokemonRepository.findById(pokemonRequestReceive.getId())
                 .ifPresentOrElse(
                         pokemon -> {
                             pokemon.updateFields(pokemonRequestReceive);
                             pokemonRepository.save(pokemon);
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
                             Pokemon test = new Pokemon(pokemonRequestReceive);
                             team.addPokemon(test);
                             teamRepository.save(team);
                         }
                 );
         sendTeamUpdate(Objects.requireNonNull(sha.getUser()).getName());
    }

    private void sendTeamUpdate(String connectionName) {
        teamRepository.findTeamByTrainer_Id(onlineUsers
                .getUser(connectionName)
                .map(User::getId)
                .orElse(null)
        ).ifPresent(team -> template.convertAndSend(
                String.format("/chat/team/%s", connectionName),
                TeamRequest.builder()
                        .teamId(team.getId())
                        .pokemon(team.getPokemon().stream()
                                .map(Pokemon::generateRequest)
                                .collect(Collectors.toList())
                        )
                        .build()
        ));
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor sha = SimpMessageHeaderAccessor.wrap(event.getMessage());
        if (Objects.requireNonNull(sha.getDestination()).startsWith("/chat/team/")) {
            sendTeamUpdate(Objects.requireNonNull(sha.getUser()).getName());
        }
    }
}
