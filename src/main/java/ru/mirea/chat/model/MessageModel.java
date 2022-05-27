package ru.mirea.chat.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
@RequiredArgsConstructor
@Getter
@Setter
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
     * Содержание сообщения / файла
     */
    private String content;
    /**
     * Название файла
     */
    private String filename;
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
     * Идентификатор стикера
     */
    private Integer sticker;

    /**
     * Перечисление возможных типов сообщения
     */
    public enum MessageType {
        CHAT, JOIN, LEAVE, FILE
    }

    /**
     * Метод получения типа сообщения
     * @return возвращает тип сообщения
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Метод установки типа сообщения
     * @param type тип сообщения
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * Метод получения контента сообщения
     * @return возвращает контент сообщения
     */
    public String getContent() {
        return content;
    }

    /**
     * Метод установки контента сообщения
     * @param content контент
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Метод получения имени пользователя
     * @return возвращает имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Метод установки имени пользователя
     * @param username имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Метод получения даты
     * @return возвращает дату
     */
    public Date getDate() {
        return date;
    }

    /**
     * Метод установки даты
     * @param date дата
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Метод получения идентификатора чата
     * @return возвращает идентификатор чата
     */
    public Integer getChatId() {
        return chatId;
    }

    /**
     * Метод установки идентификатора чата
     * @param chatId идентификатор чата
     */
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    /**
     * Метод получения имени файла
     * @return возвращает имя файла
     */
    public String getFilename() {
        return filename;
    }

    /** Метод установки имени файла
     * @param filename имя файла
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
