package com.example.pokebattlez.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.request.ChatMessage;
import com.example.pokebattlez.model.request.PrivateMessageReceive;
import com.example.pokebattlez.model.request.User;
import com.example.pokebattlez.model.request.UserMessage;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ChatService.class, OnlineUsers.class, SimpMessagingTemplate.class})
@ExtendWith(SpringExtension.class)
public class ChatServiceTest {
    @Autowired
    private ChatService chatService;

    @MockBean
    private OnlineUsers onlineUsers;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @Test
    public void testGenerateMessageResultWithDirectInputCase1() {
        // given

        // when
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(123L, "Name")));
        ChatMessage actualGenerateMessageResult = this.chatService.generateMessage("42",
                new UserMessage("Not all who wander are lost"));

        // then
        assertEquals("Not all who wander are lost", actualGenerateMessageResult.getBody());
        assertEquals("Name", actualGenerateMessageResult.getName());
        verify(this.onlineUsers).getUser(anyString());
    }

    @Test
    public void testGenerateMessageResultWithMockedUser() {
        // given
        User user = mock(User.class);
        Optional<User> ofResult = Optional.<User>of(user);

        // when
        when(user.getName()).thenReturn("foo");
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult);
        ChatMessage actualGenerateMessageResult = this.chatService.generateMessage("42",
                new UserMessage("Not all who wander are lost"));

        // then
        assertEquals("Not all who wander are lost", actualGenerateMessageResult.getBody());
        assertEquals("foo", actualGenerateMessageResult.getName());
        verify(this.onlineUsers).getUser(anyString());
        verify(user).getName();
    }

    @Test
    public void testGenerateMessageResultWithDirectInputCase2() {
        // given
        User user = mock(User.class);
        Optional<User> ofResult = Optional.<User>of(user);

        // when
        when(user.getName()).thenReturn("foo");
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult);
        this.chatService.generateMessage("42", new UserMessage("testing"));

        // then
        verify(this.onlineUsers).getUser(anyString());
        verify(user).getName();
    }

    @Test
    public void testGenerateMessageResultWithMockedOnlineUsers() {
        // given
        User user = mock(User.class);
        Optional<User> ofResult = Optional.<User>of(user);

        // when
        when(user.getName()).thenReturn("foo");
        when(this.onlineUsers.getUser(anyString())).thenReturn(ofResult);
        UserMessage userMessage = mock(UserMessage.class);
        when(userMessage.getBody()).thenReturn("foo");
        ChatMessage actualGenerateMessageResult = this.chatService.generateMessage("42", userMessage);

        // then
        assertEquals("foo", actualGenerateMessageResult.getBody());
        assertEquals("foo", actualGenerateMessageResult.getName());
        verify(this.onlineUsers).getUser(anyString());
        verify(user).getName();
        verify(userMessage).getBody();
    }

    @Test
    public void testSendPrivateMessageWithMockedOnlineUsers() {
        // when
        when(this.onlineUsers.getConId(anyLong())).thenReturn(Optional.<String>of("42"));
        when(this.onlineUsers.getUser(anyLong())).thenReturn(Optional.<User>of(new User(123L, "Name")));
        when(this.onlineUsers.getUser(anyString())).thenReturn(Optional.<User>of(new User(123L, "Name")));
        this.chatService.sendPrivateMessage("Name", new PrivateMessageReceive(1L, "Not all who wander are lost"));

        // then
        verify(this.onlineUsers).getConId(anyLong());
        verify(this.onlineUsers).getUser(anyString());
        verify(this.onlineUsers).getUser(anyLong());
    }
}

