package ru.mirea.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.chat.model.ChatModel;
import ru.mirea.chat.repository.IChatRepository;

import java.util.List;
/**
 * Класс-сервис для передачи данных из таблицы БД с чатами в контроллер
 * @author Бирюкова Екатерина
 */
@Service
@RequiredArgsConstructor
public class ChatService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с чатами
     */
    private IChatRepository iChatRepository;

    /**
     * Конструктор присваивает значение для объекта интерфейса-репозитория
     * @param iChatRepository Интерфейс-репозиторий получающий данные из таблицы БД с чатами
     */
    @Autowired
    public ChatService(IChatRepository iChatRepository){
        this.iChatRepository=iChatRepository;
    }

    /**
     * Метод для получения всех чатов
     * @return возвращает список всех чатов
     */
    public List<ChatModel> getAllChats(){
        return iChatRepository.findAll();
    }

    /**
     * Метод получения чата по идентификатору
     * @param id идентификатор чата
     * @return возвращает найденный чат
     */
    public ChatModel getChatById(int id){
        return iChatRepository.findById(id);
    }

    /**
     * Метод сохранения чата
     * @param chat чат, который надо сохранить
     */
    public void saveChat(ChatModel chat){
        iChatRepository.save(chat);
    }

    /**
     * Метод удаления чата по идентификатору
     * @param id идентификатор
     */
    public void deleteById(int id){
        iChatRepository.deleteById(id);
    }
}
