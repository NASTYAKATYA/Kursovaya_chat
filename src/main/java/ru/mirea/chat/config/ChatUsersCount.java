package ru.mirea.chat.config;

import ru.mirea.chat.model.UserModel;

import java.util.HashMap;

public class ChatUsersCount {
    private static HashMap<String, Integer> users = new HashMap<>();
    public static synchronized void addUser(String username) {
        int count = users.getOrDefault(username, 0);
        users.put(username, count + 1);
    }
    public static synchronized void removeUser(String username) {
        int count = users.getOrDefault(username, 0);
        if (count - 1 <= 0) {
            users.remove(username);
        }
        else {
            users.put(username, count - 1);
        }
    }
    public static synchronized boolean userExists(String username) {
        return users.containsKey(username);
    }
}
