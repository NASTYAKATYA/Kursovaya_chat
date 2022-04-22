package ru.mirea.chat.config;

import ru.mirea.chat.model.UserModel;

import java.util.HashMap;

/**
 * Класс для подсчета подключений определенного пользователя к чату
 * @author Бирюкова Екатерина
 */
public class ChatUsersCount {
    /**
     * Хранение данных в виде пары - Имя пользователя, Количество подключений
     */
    private static HashMap<String, Integer> users = new HashMap<>();

    /**
     * Метод добавления подключения пользователя
     * @param username Имя пользователя
     */
    public static synchronized void addUser(String username) {
        int count = users.getOrDefault(username, 0);
        users.put(username, count + 1);
    }

    /**
     * Метод для удаления подключения пользователя
     * @param username имя пользователя
     */
    public static synchronized void removeUser(String username) {
        int count = users.getOrDefault(username, 0);
        if (count - 1 <= 0) {
            users.remove(username);
        }
        else {
            users.put(username, count - 1);
        }
    }

    /**
     * Метод для проверки подключения конкретного пользователя к чату
     * @param username Имя пользователя
     * @return Результат проверки
     */
    public static synchronized boolean userExists(String username) {
        return users.containsKey(username);
    }
}
