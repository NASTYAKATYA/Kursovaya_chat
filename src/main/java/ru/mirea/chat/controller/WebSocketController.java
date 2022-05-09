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

/**
 * Данный класс является WebSocket контроллером
 * @author Бирюкова Екатерина
 */
@Controller
public class WebSocketController {
    /**
     * Класс операций по отправке сообщений адресату
     */
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Конструктор для WebSocket контроллера
     * @param messagingTemplate Объект, представляющий шаблон для сообщения
     */
    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Метод для пересылки сообщения пользователям
     * @param messageModel Модель сообщения
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MessageModel messageModel) {
        messageModel.setDate(new Date());
        messagingTemplate.convertAndSend("/topic/chat/" + messageModel.getChatId(), messageModel);
    }

    /**
     * Метод для добавления пользователя
     * @param messageModel Модель сообщения
     * @param headerAccessor Объект для доступа к заголовкам пользователя
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload MessageModel messageModel, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", messageModel.getUsername());
        var username = messageModel.getUsername();
        var chatId = messageModel.getChatId();
        var stompSessionId = headerAccessor.getSessionId();
        messageModel.setDate(new Date());
        if (!ChatUsersCount.userExists(username, chatId)) {
            sendAddUser(messageModel);
        }
        ChatUsersCount.addUser(username, stompSessionId, chatId);
    }

    /**
     * Метод для уведомления о присоединении нового пользователя
     * @param messageModel Модель сообщения
     */
    public void sendAddUser(@Payload MessageModel messageModel) {
        messagingTemplate.convertAndSend("/topic/chat/" + messageModel.getChatId(), messageModel);
    }
}
