package ru.mirea.chat.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ru.mirea.chat.config.ChatUsersCount;
import ru.mirea.chat.model.MessageModel;

import java.util.Date;

/**
 * Данный класс отслеживает все события в чате
 * @author Бирюкова Екатерина
 */
@Component
public class WebSocketEventListener {
    /**
     * Класс операций по отправке сообщений адресату
     */
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Конструктор класса
     * @param messagingTemplate Объект, представляющий шаблон для сообщения
     */
    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Метод для отслеживания события отключения пользователя
     * @param event Событие, возникающее при закрытии сеанса клиента WebSocket
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        var stompSessionId = headerAccessor.getSessionId();
        var chatId = ChatUsersCount.getChatIdByStompSessionId(stompSessionId);
        if(username != null) {
            ChatUsersCount.removeUser(stompSessionId);
            if (!ChatUsersCount.userExists(username, chatId)) {
                MessageModel messageModel = new MessageModel();
                messageModel.setType(MessageModel.MessageType.LEAVE);
                messageModel.setUsername(username);
                messageModel.setDate(new Date());
                messagingTemplate.convertAndSend("/topic/chat/" + chatId, messageModel);
            }
        }
    }
}
