package com.example.pokebattlez.battle.model.packet;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class BattleResultsDTO {
    private List<Long> winners;
    private List<Long> losers;
}
