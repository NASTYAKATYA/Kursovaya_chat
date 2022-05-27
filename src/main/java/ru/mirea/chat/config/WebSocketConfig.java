package ru.mirea.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * Класс-конфигурация позволяет настраивать параметры WebSocket путем переопределения методов
 * @author Бирюкова Екатерина
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * Метод для регистрации конечной точки STOMP, сопоставляющий ее с /ws
     * @param registry Объект, используемый для соглашения на регистрацию STOMP через конечную точку WebSocket
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    /**
     * Метод для настройки брокера сообщений
     * @param registry Объект для настройки параметров брокера сообщений
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }

    /**
     * Метод для настройки параметров, связанных с обработкой сообщений, полученных от клиентов WebSocket и отправленных им
     * @param registration Объект для обработки сообщения
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(5000000); // default : 64 * 1024
        registration.setSendTimeLimit(30 * 10000); // default : 10 * 10000
        registration.setSendBufferSizeLimit(5* 512 * 1024); // default : 512 * 1024
    }
}
