package com.example.pokebattlez.controller;

import com.example.pokebattlez.model.request.ChatMessage;
import com.example.pokebattlez.model.request.UserMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {
    private static final String USER = "Prof. Oak"; // TODO Replace with username

    @MessageMapping("/message/lobby")
    @SendTo("/chat/lobby")
    public ChatMessage lobbyChat(UserMessage userMessage) {
        return new ChatMessage(USER, HtmlUtils.htmlEscape(userMessage.getBody()));
    }

    @MessageMapping("/message/battle/{id}")
    @SendTo("/chat/battle/{id}")
    public ChatMessage battleChat(UserMessage userMessage, @DestinationVariable int id) {
        return new ChatMessage(USER, HtmlUtils.htmlEscape(userMessage.getBody()));
    }
}
