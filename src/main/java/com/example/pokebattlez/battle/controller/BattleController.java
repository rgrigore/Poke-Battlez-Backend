package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.service.BattleService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/battle")
public class BattleController {

    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @MessageMapping("/{battleId}/move")
    public void chooseMove(@DestinationVariable String battleId, Request request) {
        System.out.println(request);
        battleService.useMove(battleId, request.playerId, request.move, request.targetId);
    }

    @MessageMapping("/{battleId}/switch")
    public void choosePokemon(@DestinationVariable String battleId, Request request) {
        battleService.switchPokemon(battleId, request.playerId, request.index);
    }

    @MessageMapping("/{battleId}/cancel")
    public void cancelAction(@DestinationVariable String battleId, Request request) {
        battleService.cancelAction(battleId, request.playerId);
    }

    @MessageMapping("/{battleId}/connect")
    public void connect(@DestinationVariable String battleId, Request request) {
        battleService.connectTrainer(battleId, request.getPlayerId());
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Request {
        private Long playerId;
        private Integer index;
        private String move;
        private Long targetId;
    }
}
