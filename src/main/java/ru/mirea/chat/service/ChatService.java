package ru.mirea.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.chat.model.ChatModel;
import ru.mirea.chat.repository.IChatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private IChatRepository iChatRepository;

    @Autowired
    public ChatService(IChatRepository iChatRepository){
        this.iChatRepository=iChatRepository;
    }
    public List<ChatModel> getAllChats(){
        return iChatRepository.findAll();
    }
    public ChatModel getChatById(int id){
        return iChatRepository.findById(id);
    }

    public void saveChat(ChatModel chat){
        iChatRepository.save(chat);
    }
    public void deleteById(int id){
        iChatRepository.deleteById(id);
    }
}
