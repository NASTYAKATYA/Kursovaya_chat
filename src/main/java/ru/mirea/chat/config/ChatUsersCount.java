package ru.mirea.chat.config;

import ru.mirea.chat.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс для подсчета подключений определенного пользователя к определенному чату
 * @author Бирюкова Екатерина
 */
public class ChatUsersCount {
    /**
     * Класс для удобного хранения информации о сессии пользователя в определенном чате
     */
    private static class ChatUser {
        private String username;
        private String stompSessionId;
        private Integer chatId;
        public ChatUser(String username, String stompSessionId, Integer chatId) {
            this.username = username;
            this.stompSessionId = stompSessionId;
            this.chatId = chatId;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getStompSessionId() {
            return stompSessionId;
        }
        public void setStompSessionId(String stompSessionId) {
            this.stompSessionId = stompSessionId;
        }
        public Integer getChatId() {
            return chatId;
        }
        public void setChatId(Integer chatId) {
            this.chatId = chatId;
        }
    }
    private static List<ChatUser> users = new ArrayList<>();

    /**
     * Метод добавления подключения пользователя
     * @param username Имя пользователя
     * @param stompSessionId Идентификатор сессии Stomp
     * @param chatId Идентификатор чата
     */
    public static synchronized void addUser(String username, String stompSessionId, Integer chatId) {
        users.add(new ChatUser(username, stompSessionId, chatId));
    }

    /**
     * Метод для удаления подключения пользователя
     * @param stompSessionId Идентификатор сессии Stomp
     */
    public static synchronized void removeUser(String stompSessionId) {
        users.removeIf(chatUser -> chatUser.getStompSessionId().equals(stompSessionId));
    }

    /**
     * Метод для проверки подключения конкретного пользователя к чату
     * @param username Имя пользователя
     * @param chatId Идентификатор чата
     * @return Результат проверки
     */
    public static synchronized boolean userExists(String username, Integer chatId) {
        var user =  users.stream()
                .filter(chatUser -> chatUser.getUsername().equals(username) && chatUser.getChatId().equals(chatId))
                .findAny()
                .orElse(null);
        return user != null;
    }

    /**
     * Метод для получения идентификатора чата по идентификатору сессии Stomp (Метод используется
     * в событии отключения от чата, чтобы понимать, из какого конкретно чата вышел пользователь)
     * @param stompSessionId Идентификатор сессии Stomp
     * @return Идентификатор чата
     */
    public static synchronized Integer getChatIdByStompSessionId(String stompSessionId) {
        var user =  users.stream()
                .filter(chatUser -> chatUser.getStompSessionId().equals(stompSessionId))
                .findAny()
                .orElse(null);
        if (user != null)
            return user.chatId;
        else
            return null;
    }
}
