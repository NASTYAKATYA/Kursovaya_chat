package ru.mirea.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.chat.model.ChatModel;

import java.util.List;

@Repository
public interface IChatRepository extends JpaRepository<ChatModel, Integer> {
    ChatModel findByName(String name);
    ChatModel findById(int id);
    Long deleteById(int id);
}
