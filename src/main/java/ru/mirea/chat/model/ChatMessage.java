package ru.mirea.chat.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class ChatMessage {
    private String content;
    private Date timestamp;
    private String sender;

}
