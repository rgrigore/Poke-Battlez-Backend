package com.example.pokebattlez.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeResponse {
    private Boolean accept;
    private Long from;
}
