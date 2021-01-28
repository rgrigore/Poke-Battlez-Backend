package com.example.pokebattlez.model.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountConfirmation {
    private boolean state;
    private long id;
    private String username;
}
