package ru.mirea.chat.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.mirea.chat.model.ChatMessage;


@Controller
public class WebSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/chatRoom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
