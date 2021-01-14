package com.example.pokebattlez.controller;

import com.example.pokebattlez.model.OnlineUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledController {
    private static final int UPDATE_INTERVAL = 100;

    private final SimpMessagingTemplate template;
    private final OnlineUsers onlineUsers;

    @Autowired
    public ScheduledController(SimpMessagingTemplate template, OnlineUsers onlineUsers) {
        this.template = template;
        this.onlineUsers = onlineUsers;
    }

    @Scheduled(fixedRate = UPDATE_INTERVAL)
    public void periodicOnlineUsers() {
        if (onlineUsers.hasChanged()) {
            template.convertAndSend("/chat/lobby/users", onlineUsers.getUsers().toArray());
        }
    }
}
