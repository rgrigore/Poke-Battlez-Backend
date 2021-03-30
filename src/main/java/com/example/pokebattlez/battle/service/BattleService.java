package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.controller.ActionManager;
import com.example.pokebattlez.battle.controller.Battle;
import com.example.pokebattlez.battle.model.BasicPokemon;
import com.example.pokebattlez.battle.model.BattlePokemon;
import com.example.pokebattlez.battle.model.packet.BattleResultsDTO;
import com.example.pokebattlez.controller.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BattleService {
    private final SimpMessagingTemplate template;
    private final TeamRepository teamRepository;
    private final PokemonService pokemonService;

    private final static List<Battle> battles = new ArrayList<>();

    public void register(Battle battle) {
        battle.setBattleService(this);
        battles.add(battle);
    }

    public void useMove(String battleId ,Long trainerId, String moveName, Long targetId) {
//        findBattle(battleId).ifPresent(battle -> battle.registerUseMove(trainerId, moveName, targetId));
    }

    public void switchPokemon(String battleId, Long trainerId, int pokemonPosition) {
//        findBattle(battleId).ifPresent(battle -> battle.registerSwitchPokemon(trainerId, pokemonPosition));
    }

    public void cancelAction(String battleId, Long trainerId) {
//        findBattle(battleId).ifPresent(battle -> battle.cancelAction(trainerId));
    }

    public Optional<Battle> findBattle(String battleId) {
        return battles.stream().filter(battle -> battle.getId().toString().equals(battleId)).findFirst();
    }

    public String generateBattle(Long challenger, Long ... challenged) { // TODO This needs to be refactored for microservices
        List<Long> trainers = new ArrayList<>(List.of(challenged));
        trainers.add(challenger);

        Battle battle = new Battle(); // TODO Refactor to use a builder

        trainers.stream().map(teamRepository::findTeamByTrainer_Id).forEach(teamOptional -> teamOptional.ifPresent(team ->
            battle.registerPokemonTeam(
                    team.getTrainer().getId(),
                    team.getPokemon().stream()
                            .map(BattlePokemon::new)
                            .peek(battlePokemon -> battlePokemon.setPokemonService(pokemonService))
                            .collect(Collectors.toList())
            )
        ));

        battle.setActionManager(ActionManager.standard());

        register(battle);

        return battle.getId().toString();
    }

    public void connectTrainer(String battleId, Long trainer) {
//        battles.stream().filter(battle -> battle.getId().toString().equals(battleId)).findFirst()
//                .ifPresent(battle -> battle.connectTrainer(trainer));
    }

//    public void startBattle(String id) {
//        battles.stream().filter(battle -> battle.getId().toString().equals(id)).findFirst().ifPresent(Battle::start);
//    }

    public void sendBattleState(Battle battle) {
//        Map<Long, Integer> active = battle.getTrainers().stream().collect(Collectors.toMap(
//                trainer -> trainer,
//                trainer -> battle.getActivePokemon().get(trainer).getPosition())
//        );
//
//        Map<Long, Map<Long, Integer>> currentHealths = battle.getTrainers().stream()
//                .collect(Collectors.toMap(trainer -> trainer, trainer -> new HashMap<>()));
//        battle.getPokemon().forEach((key, value) -> value.forEach(battlePokemon ->
//                currentHealths.get(key).put(battlePokemon.getId(), battlePokemon.getCurrentHp())
//        ));
//
//        Map<Long, List<BattleState.OpponentPokemon>> opponents = new HashMap<>();
//        battle.getTrainers().forEach(trainer -> {
//            battle.getPokemon().entrySet().stream().filter(entry -> !entry.getKey().equals(trainer)).findFirst().ifPresent(entry -> {
//                opponents.put(
//                    trainer,
//                    entry.getValue().stream().map(pokemon -> {
//                        BattleState.OpponentPokemon opponentPokemon = new BattleState.OpponentPokemon(pokemon);
//                        opponentPokemon.setActive(battle.getActivePokemon().get(entry.getKey()).equals(pokemon));
//                        return opponentPokemon;
//                    }).collect(Collectors.toList())
//                );
//            });
//        });
//
//        template.convertAndSend(
//                String.format("/battle/%s", battle.getId().toString()),
//                BattleState.builder()
//                        .log(battle.getTurnLog())
//                        .order(battle.getTurnOrder())
//                        .active(active)
//                        .currentHealths(currentHealths)
//                        .opponents(opponents)
//                        .build()
//        );
    }

    public void sendTeam(String battleId, Long trainerId, List<BattlePokemon> pokemon) {
        String route = String.format("/battle/%s/team/%s", battleId, trainerId);
        List<BasicPokemon> basicPokemon = pokemon.stream().map(BasicPokemon::new).collect(Collectors.toList());

        template.convertAndSend(
                route,
                basicPokemon
        );
    }

    public void sendBattleResults(String battleId, List<Long> winners, List<Long> losers) {
        template.convertAndSend(
                String.format("/battle/%s/results", battleId),
                BattleResultsDTO.builder()
                        .winners(winners)
                        .losers(losers)
                        .build()
        );
    }
}
