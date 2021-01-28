package com.example.pokebattlez.model.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private long id;
    private String name;
}
