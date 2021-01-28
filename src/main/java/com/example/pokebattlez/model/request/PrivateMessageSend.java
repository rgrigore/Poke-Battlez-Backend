package com.example.pokebattlez.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageSend {
    private boolean sender;
    private User from;
    private User to;
    private String body;
}
