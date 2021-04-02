package com.example.pokebattlez.battle.model.packet;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BattleResultsDTO {
    private List<Long> winners;
    private List<Long> losers;
}
