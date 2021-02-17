package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.controller.Battle;
import com.example.pokebattlez.battle.model.Pokemon;
import com.example.pokebattlez.battle.model.packet.BattleState;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BattleService {
    private final SimpMessagingTemplate template;
    private final static List<Battle> battles = new ArrayList<>();

    public BattleService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void register(Battle battle) {
        battle.setBattleService(this);
        battles.add(battle);
    }

    public void useMove(String battleId ,Long trainerId, int movePosition, Long targetId) {
        findBattle(battleId).ifPresent(battle -> battle.registerUseMove(trainerId, movePosition, targetId));
    }

    public void switchPokemon(String battleId, Long trainerId, int pokemonPosition) {
        findBattle(battleId).ifPresent(battle -> battle.registerSwitchPokemon(trainerId, pokemonPosition));
    }

    public void cancelAction(String battleId, Long trainerId) {
        findBattle(battleId).ifPresent(battle -> battle.cancelAction(trainerId));
    }

    public Optional<Battle> findBattle(String battleId) {
        return battles.stream().filter(battle -> battle.getId().toString().equals(battleId)).findFirst();
    }

    public void sendBattleState(Battle battle) {
        Map<Long, Long> active = battle.getTrainers().stream().collect(Collectors.toMap(
                trainer -> trainer,
                trainer -> battle.getActivePokemon().get(trainer).getId())
        );

        Map<Long, Map<Long, Integer>> currentHealths = battle.getTrainers().stream()
                .collect(Collectors.toMap(trainer -> trainer, trainer -> new HashMap<>()));
        battle.getPokemon().forEach((key, value) -> value.forEach(pokemon ->
                currentHealths.get(key).put(pokemon.getId(), pokemon.getCurrentHp())
        ));

        template.convertAndSend(
                String.format("/battle/%s", battle.getId().toString()),
                BattleState.builder()
                        .log(battle.getTurnLog())
                        .order(battle.getTurnOrder())
                        .active(active)
                        .currentHealths(currentHealths)
                        .build()
        );
    }
}
