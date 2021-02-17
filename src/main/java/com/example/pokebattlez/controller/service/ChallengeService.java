package com.example.pokebattlez.controller.service;

import com.example.pokebattlez.battle.service.BattleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ChallengeService {

    private final BattleService battleService;
    private final SimpMessagingTemplate template;

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

    private void startBattle(Challenge challenge) {
        String id = battleService.generateBattle(challenge.challenger, challenge.challenged.toArray(new Long[0]));
//        template.convertAndSend();
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

    private static class BattleData {
        private List<Long> trainers;
        private Map<Long, String> trainerNames;
        private Map<Long, List<Integer>> availablePokemon;
    }
}
