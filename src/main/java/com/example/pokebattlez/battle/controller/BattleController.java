package com.example.pokebattlez.battle.controller;

import com.example.pokebattlez.battle.service.BattleService;
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

    @MessageMapping("/move")
    public void chooseMove(@RequestParam("id") Long playerId, @RequestParam("index") Integer index) {

    }
}
