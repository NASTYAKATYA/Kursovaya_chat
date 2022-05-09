package ru.mirea.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "chats")
public class ChatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;
     @Column(name="creator")
    String creator;

}
