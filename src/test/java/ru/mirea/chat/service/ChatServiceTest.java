package ru.mirea.chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.chat.model.ChatModel;
import ru.mirea.chat.repository.IChatRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;
    @Mock
    private IChatRepository icr;
    @Captor
    private ArgumentCaptor<ChatModel> captor;

    private ChatModel chat1, chat2;
    @BeforeEach
    void setUp() {
        chat1 = new ChatModel();
        chat1.setId(1);
        chat1.setName("Chat1");

        chat2 = new ChatModel();
        chat2.setId(2);
        chat2.setName("Chat2");
    }

    @Test
    void getAllChats() {
        Mockito.when(icr.findAll()).thenReturn(List.of(chat1, chat2));
        assertEquals(2,icr.findAll().size());
    }

    @Test
    void getChatById() {
        Mockito.when(icr.findById(1)).thenReturn(chat1);
        assertEquals(chat1, icr.findById(1));
    }

    @Test
    void saveChat() {
        chatService.saveChat(chat1);
        Mockito.verify(icr).save(captor.capture());
        ChatModel capturedChat = captor.getValue();
        assertEquals("Chat1", capturedChat.getName());
    }

    @Test
    void deleteById() {
        chatService.deleteById(1);
        Mockito.verify(icr).deleteById(1);
    }
}