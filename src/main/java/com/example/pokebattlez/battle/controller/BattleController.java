package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.service.BattleService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@MessageMapping("/battle")
public class BattleController {

    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @MessageMapping("/{battleId}/move")
    public void chooseMove(@DestinationVariable String battleId, @RequestParam("id") Long playerId, @RequestParam("index") Integer index, @RequestParam("target") Long targetId) {
        battleService.useMove(battleId, playerId, index, targetId);
    }

    @MessageMapping("/{battleId}/switch")
    public void choosePokemon(@DestinationVariable String battleId, @RequestParam("id") Long playerId, @RequestParam("index") Integer index) {
        battleService.switchPokemon(battleId, playerId, index);
    }

    @MessageMapping("/{battleId}/cancel")
    public void cancelAction(@DestinationVariable String battleId, @RequestParam("id") Long playerId) {
        battleService.cancelAction(battleId, playerId);
    }
}
