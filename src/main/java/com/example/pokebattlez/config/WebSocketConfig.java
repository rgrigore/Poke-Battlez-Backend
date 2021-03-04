package com.example.pokebattlez.config;

import com.example.pokebattlez.model.OnlineUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final OnlineUsers onlineUsers;

    @Autowired
    public WebSocketConfig(OnlineUsers onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chat", "/account", "/battle");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/account-management").setAllowedOriginPatterns("*");
        registry.addEndpoint("/battle").setAllowedOriginPatterns("*");
        registry.addEndpoint("/chat-lobby")
                .setHandshakeHandler(new LobbyHandshakeHandler())
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null && StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    if (Objects.equals(accessor.getDestination(), "/chat/lobby/users")) {
//                        System.out.println(accessor.getUser().getName()); // TODO Remove this
                        onlineUsers.addUser(
                            Integer.parseInt(Objects.requireNonNull(accessor.getFirstNativeHeader("user"))),
                            Objects.requireNonNull(accessor.getUser()).getName()
                        );
                    }
                } else if (accessor != null && StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    onlineUsers.removeUser(Objects.requireNonNull(accessor.getUser()).getName());
                }
                return message;
            }
        });
    }
}
