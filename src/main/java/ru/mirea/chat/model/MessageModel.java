package ru.mirea.chat.model;

public class MessageModel {
    private MessageType type;
    private String content;
    private String username;
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
