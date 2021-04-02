package com.example.pokebattlez.battle.service;

import com.example.pokebattlez.battle.controller.ActionManager;
import com.example.pokebattlez.battle.controller.Battle;
import com.example.pokebattlez.battle.controller.BattleAction;
import com.example.pokebattlez.battle.model.BasicPokemon;
import com.example.pokebattlez.battle.model.BattlePokemon;
import com.example.pokebattlez.battle.model.packet.BattleResultsDTO;
import com.example.pokebattlez.battle.model.packet.BattleStateDTO;
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
    private final static Map<String, List<Long>> connectedTrainers = new HashMap<>();

    public Optional<Battle> findBattle(String battleId) {
        return battles.stream().filter(battle -> battle.getId().toString().equals(battleId)).findFirst();
    }

    // This needs to be refactored for microservices, consider sending the teams for each trainer
    public String generateBattle(Long challenger, Long ... challenged) {
        Battle battle = Battle.builder()
                .battleService(this)
                .actionManager(ActionManager.standard())
                .build();

        addTrainerToBattle(battle, challenger);
        Arrays.stream(challenged).forEach(trainer -> addTrainerToBattle(battle, trainer));

        battles.add(battle);
        connectedTrainers.put(battle.getId().toString(), new ArrayList<>());

        return battle.getId().toString();
    }

    public void addTrainerToBattle(Battle battle, Long trainerId) {
        battle.registerTrainer(
                trainerId,
                teamRepository.findTeamByTrainer_Id(trainerId)
                        .orElseThrow(IllegalArgumentException::new)
                        .getPokemon().stream().map(BattlePokemon::new)
                        .peek(pokemon -> pokemon.setPokemonService(pokemonService))
                        .collect(Collectors.toList())
        );
    }

    public void connectTrainer(String battleId, Long trainer) {
        connectedTrainers.get(battleId).add(trainer);

        findBattle(battleId).ifPresent(battle -> {
            if (connectedTrainers.get(battleId).containsAll(battle.getTrainers())) {
                startBattle(battle);
            }
        });
    }

    public void useMove(String battleId ,Long trainerId, int movePosition, List<Long> targetTrainers) {
        findBattle(battleId).ifPresent(battle -> {
            battle.getActionManager().registerAction(
                    BattleAction.Move.builder()
                            .battle(battle)
                            .trainerId(trainerId)
                            .movePosition(movePosition)
                            .targetTrainers(targetTrainers)
                            .build()
            );

            if (turnReady(battle)) {
                playTurn(battle);
            }
        });
    }

    public void switchPokemon(String battleId, Long trainerId, int pokemonPosition) {
        findBattle(battleId).ifPresent(battle -> {
            battle.getActionManager().registerAction(
                    BattleAction.Switch.builder()
                            .battle(battle)
                            .trainerId(trainerId)
                            .targetPosition(pokemonPosition)
                            .build()
            );

            if (turnReady(battle)) {
                playTurn(battle);
            }
        });
    }

    public void cancelAction(String battleId, Long trainerId) {
        findBattle(battleId).ifPresent(battle ->
                battle.getActionManager().cancelTrainerAction(trainerId)
        );
    }

    public boolean turnReady(Battle battle) {
        return battle.getActionManager().getRegisteredTrainers().containsAll(battle.getTrainers());
    }

    public void startBattle(Battle battle) {
        battle.getPokemon().forEach((trainer, team) -> sendTeam(battle.getId().toString(), trainer, team));
        sendBattleState(battle);
    }

    public void playTurn(Battle battle) {
        battle.executeTurn();
        sendBattleState(battle);
        if (battle.battleIsOver()) {
            sendBattleResults(
                    battle.getId().toString(),
                    battle.getWinners(),
                    battle.getLosers()
            );
        }
    }

    public void sendBattleState(Battle battle) {
        Map<Long, Integer> active = battle.getTrainers().stream().collect(Collectors.toMap(
                trainer -> trainer,
                trainer -> battle.getActivePokemon().get(trainer).getPosition())
        );

        Map<Long, Map<Long, Integer>> currentHealths = battle.getTrainers().stream()
                .collect(Collectors.toMap(trainer -> trainer, trainer -> new HashMap<>()));
        battle.getPokemon().forEach((key, value) -> value.forEach(battlePokemon ->
                currentHealths.get(key).put(battlePokemon.getId(), battlePokemon.getCurrentHp())
        ));

        Map<Long, List<BattleStateDTO.OpponentPokemon>> opponents = new HashMap<>();
        battle.getTrainers().forEach(trainer ->
                battle.getPokemon().entrySet().stream()
                        .filter(entry -> !entry.getKey().equals(trainer)).findFirst()
                        .ifPresent(entry ->
                                opponents.put(
                                        trainer,
                                        entry.getValue().stream().map(pokemon -> {
                                            BattleStateDTO.OpponentPokemon opponentPokemon = new BattleStateDTO.OpponentPokemon(pokemon);
                                            opponentPokemon.setActive(battle.getActivePokemon().get(entry.getKey()).equals(pokemon));
                                            return opponentPokemon;
                                        }).collect(Collectors.toList())
                                )
                        )
        );

        template.convertAndSend(
                String.format("/battle/%s", battle.getId().toString()),
                BattleStateDTO.builder()
                        .log(battle.getTurnLog())
                        .order(battle.getTurnOrder())
                        .active(active)
                        .currentHealths(currentHealths)
                        .opponents(opponents)
                        .build()
        );
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
