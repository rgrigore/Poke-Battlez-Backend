package com.example.pokebattlez.battle.model.packet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleState {
    private List<String> log;
    private List<Long> order;
    private Map<Long, Long> active;
    private Map<Long, Map<Long, Integer>> currentHealths;
}
