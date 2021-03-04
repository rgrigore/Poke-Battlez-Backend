package com.example.pokebattlez.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountConfirmation {
    private boolean state;
    private long id;
    private String username;
    private String email;
    private List<String> roles = new ArrayList<>();
    private String token;
}
