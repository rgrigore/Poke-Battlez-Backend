package com.example.pokebattlez.controller;

import com.example.pokebattlez.controller.service.ChallengeService;
import com.example.pokebattlez.controller.service.ChatService;
import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.request.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LobbyController {

    private static final int UPDATE_INTERVAL = 100;

    private final SimpMessagingTemplate template;
    private final OnlineUsers onlineUsers;
    private final ChatService chatService;
    private final ChallengeService challengeService;

    @MessageMapping("/message/lobby")
    @SendTo("/chat/lobby")
    public ChatMessage lobbyChat(SimpMessageHeaderAccessor sha, UserMessage userMessage) {
        return chatService.generateMessage(
                Objects.requireNonNull(sha.getUser()).getName(),
                userMessage
        );
    }

    @MessageMapping("/chat/private")
    public void privateChat(SimpMessageHeaderAccessor sha, PrivateMessageReceive pmr) {
        chatService.sendPrivateMessage(
                Objects.requireNonNull(sha.getUser()).getName(),
                pmr
        );

    }

    @MessageMapping("/chat/challenge")
    public void challenge(SimpMessageHeaderAccessor sha, ChallengeReceive chr) {
        challengeService.sendChallenge(
                Objects.requireNonNull(sha.getUser()).getName(),
                chr
        );
    }

    @MessageMapping("/chat/challenge/accept")
    public void acceptChallenge(SimpMessageHeaderAccessor sha, ChallengeResponse cr) {
        challengeService.respond(
                Objects.requireNonNull(sha.getUser()).getName(),
                cr
        );
    }

    @Scheduled(fixedRate = UPDATE_INTERVAL)
    public void periodicOnlineUsers() {
        if (onlineUsers.hasChanged()) {
            template.convertAndSend("/chat/lobby/users", onlineUsers.getUsers().toArray());
        }
    }
}
