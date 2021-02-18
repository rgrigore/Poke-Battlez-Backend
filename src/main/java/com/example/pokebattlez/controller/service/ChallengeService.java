package com.example.pokebattlez.controller.service;

import com.example.pokebattlez.battle.service.BattleService;
import com.example.pokebattlez.controller.repository.TeamRepository;
import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.entity.Pokemon;
import com.example.pokebattlez.model.entity.Team;
import com.example.pokebattlez.model.request.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ChallengeService {

    private final BattleService battleService;
    private final SimpMessagingTemplate template;
    private final OnlineUsers onlineUsers;
    private final TeamRepository teamRepository;

    private final List<Challenge> challenges = new ArrayList<>();

    public void register(Long challenger, Long ... challenged) {
        challenges.add(new Challenge(challenger, List.of(challenged)));
    }

    public boolean hasActiveChallenge(Long user) {
        return challenges.stream().anyMatch(challenge -> challenge.challenger.equals(user));
    }

    public void accept(Long user, Long from) {
        challenges.stream().filter(challenge -> challenge.challenger.equals(from)).findFirst().ifPresent(challenge -> {
            challenge.accepts.add(user);
            if (challenge.isAccepted()) {
                challenges.remove(challenge);
                startBattle(challenge);
            }
        });
    }

    public void decline(Long from) {
        challenges.stream().filter(challenge -> challenge.challenger.equals(from)).findFirst()
                .ifPresent(challenges::remove);
    }

    private void startBattle(Challenge challenge) { // TODO Check if all trainers are still online
        String battleId = battleService.generateBattle(challenge.challenger, challenge.challenged.toArray(new Long[0]));

        List<String> connections = challenge.challenged.stream().map(challenged1 -> onlineUsers.getConId(challenged1).orElse(null)).collect(Collectors.toList());
        connections.add(onlineUsers.getConId(challenge.challenger).orElse(null));

        BattleData battleData = new BattleData(battleId);
        connections.stream().filter(Objects::nonNull).forEach(connection ->
                onlineUsers.getUser(connection).ifPresent(user -> {
                    battleData.trainers.add(user.getId());
                    battleData.trainerNames.put(user.getId(), user.getName());
                    teamRepository.findTeamByTrainer_Id(user.getId()).stream()
                            .peek(team -> battleData.trainerTeams.put(user.getId(), team.getId()))
                            .map(Team::getPokemon).forEach(team ->
                                    battleData.availablePokemon.put(user.getId(), team.stream()
                                            .map(Pokemon::getPosition)
                                            .collect(Collectors.toList())
                                    )
                            );
                })
        );

//        System.out.println(connections);
        connections.forEach(connection -> {
            System.out.printf("/battle/start/%s%n", connection);
            template.convertAndSend(String.format("/battle/start/%s", connection), battleData);
        });

//        battleService.startBattle(battleId);
    }

    @AllArgsConstructor
    private static class Challenge {
        private final Long challenger;
        private final List<Long> challenged;

        private final List<Long> accepts = new ArrayList<>();

        private boolean isAccepted() {
            return accepts.containsAll(challenged);
        }
    }

    @Data
    private static class BattleData {
        private String battleId;
        private List<Long> trainers = new ArrayList<>();
        private Map<Long, String> trainerNames = new HashMap<>();
        private Map<Long, Long> trainerTeams = new HashMap<>();
        private Map<Long, List<Integer>> availablePokemon = new HashMap<>();

        public BattleData(String battleId) {
            this.battleId = battleId;
        }
    }
}
