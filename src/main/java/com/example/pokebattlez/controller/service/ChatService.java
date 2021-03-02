package com.example.pokebattlez.controller.service;

import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatService {

    private final OnlineUsers onlineUsers;
    private final SimpMessagingTemplate template;

    public ChatMessage generateMessage(String id, UserMessage message) {
        return ChatMessage.builder()
                .name(onlineUsers
                        .getUser(id)
                        .map(User::getName)
                        .orElse("Misingno")
                )
                .body(HtmlUtils.htmlEscape(message.getBody()))
                .build();
    }

    public void sendPrivateMessage(String name, PrivateMessageReceive pmr) {
        PrivateMessageSend pms = PrivateMessageSend.builder()
                .from(onlineUsers.getUser(name).orElse(null))
                .to(onlineUsers.getUser(pmr.getTo()).orElse(null))
                .body(pmr.getBody())
                .build();

        template.convertAndSend(String.format("/chat/private/%s", name), pms.sender(true));
        template.convertAndSend(String.format("/chat/private/%s", onlineUsers.getConId(pmr.getTo()).orElse(null)), pms.sender(false));
    }
}
