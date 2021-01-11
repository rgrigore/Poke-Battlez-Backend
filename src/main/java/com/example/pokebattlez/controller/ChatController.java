package com.example.pokebattlez.controller;

import com.example.pokebattlez.model.ChatMessage;
import com.example.pokebattlez.model.UserMessage;
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
}
