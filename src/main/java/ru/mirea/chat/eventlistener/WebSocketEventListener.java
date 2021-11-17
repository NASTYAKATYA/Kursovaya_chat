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

@Component
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            ChatUsersCount.removeUser(username);
            if (!ChatUsersCount.userExists(username)) {
                MessageModel messageModel = new MessageModel();
                messageModel.setType(MessageModel.MessageType.LEAVE);
                messageModel.setUsername(username);
                messageModel.setDate(new Date());
                messagingTemplate.convertAndSend("/topic/chat", messageModel);
            }
        }
    }
}
