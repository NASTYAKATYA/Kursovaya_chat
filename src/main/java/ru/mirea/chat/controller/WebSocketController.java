package ru.mirea.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.mirea.chat.model.MessageModel;

import java.util.Date;

@Controller
public class WebSocketController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat")
    public MessageModel sendMessage(@Payload MessageModel messageModel) {
        messageModel.setDate(new Date());
        return messageModel;
    }
}
