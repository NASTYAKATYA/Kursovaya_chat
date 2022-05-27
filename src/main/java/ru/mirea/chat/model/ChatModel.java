package ru.mirea.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/**
 * Класс модели представления для чатов
 * @author Бирюкова Екатерина
 */
@Entity
@Getter
@Setter
@Table(name = "chats")
public class ChatModel {
    /**
     * Идентификатор чата
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    /**
     * Название чата
     */
    @Column(name = "name")
    String name;
    /**
     * Описание чата
     */
    @Column(name = "description")
    String description;
    /**
     * Создатель чата
     */
     @Column(name="creator")
    String creator;

}
