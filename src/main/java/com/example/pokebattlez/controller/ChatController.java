package com.example.pokebattlez.controller;

import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.request.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ChatController {

    private final SimpMessagingTemplate template;
    private final OnlineUsers onlineUsers;

    @MessageMapping("/message/lobby")
    @SendTo("/chat/lobby")
    public ChatMessage lobbyChat(SimpMessageHeaderAccessor sha, UserMessage userMessage) {
        return new ChatMessage(
            onlineUsers
                    .getUser(Objects.requireNonNull(sha.getUser()).getName())
                    .map(User::getName)
                    .orElse("Misingno"),
            HtmlUtils.htmlEscape(userMessage.getBody())
        );
    }

    @MessageMapping("/chat/private")
    public void privateChat(SimpMessageHeaderAccessor sha, PrivateMessageReceive pmr) {
        PrivateMessageSend pms = PrivateMessageSend.builder()
                .from(onlineUsers
                        .getUser(Objects.requireNonNull(sha.getUser()).getName())
                        .orElse(null)
                )
                .to(onlineUsers
                        .getUser(pmr.getTo())
                        .orElse(null)
                )
                .body(pmr.getBody())
                .sender(true)
                .build();

        template.convertAndSend(String.format("/chat/private/%s", sha.getUser().getName()), pms);
        pms.setSender(false);
        template.convertAndSend(String.format("/chat/private/%s", onlineUsers.getConId(pmr.getTo())), pms);
    }
}
