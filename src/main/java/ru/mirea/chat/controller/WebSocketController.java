package ru.mirea.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import ru.mirea.chat.config.ChatUsersCount;
import ru.mirea.chat.model.MessageModel;

import java.util.Date;

@Controller
public class WebSocketController {
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MessageModel messageModel) {
        messageModel.setDate(new Date());
        messagingTemplate.convertAndSend("/topic/chat", messageModel);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload MessageModel messageModel, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", messageModel.getUsername());
        messageModel.setDate(new Date());
        if (!ChatUsersCount.userExists(messageModel.getUsername())) {
            sendAddUser(messageModel);
        }
        ChatUsersCount.addUser(messageModel.getUsername());
    }
    public void sendAddUser(@Payload MessageModel messageModel) {
        messagingTemplate.convertAndSend("/topic/chat", messageModel);
    }
}
