package ru.mirea.chat.model;

import lombok.RequiredArgsConstructor;

import java.util.Date;
@RequiredArgsConstructor
/**
 * Класс модели представления для сообщения
 * @author Бирюкова Екатерина
 */
public class MessageModel {
    /**
     * Тип сообщения
     */
    private MessageType type;
    /**
     * Содержание сообщения
     */
    private String content;
    /**
     * Имя отправителя
     */
    private String username;
    /**
     * Дата и время отправки сообщения
     */
    private Date date;

    /**
     * Идентификатор чата
     */
    private Integer chatId;

    /**
     * Перечисление возможных типов сообщения
     */
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Integer getChatId() {
        return chatId;
    }
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }
}
