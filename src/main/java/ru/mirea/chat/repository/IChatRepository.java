package ru.mirea.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.chat.model.ChatModel;

import java.util.List;
/**
 * Интерфейс для получения информации о чатах из таблиц базы данных
 * @author Бирюкова Екатерина
 */
@Repository
public interface IChatRepository extends JpaRepository<ChatModel, Integer> {
    /**
     * Метод поиска по названию
     * @param name название
     * @return возвращает найденный чат
     */
    ChatModel findByName(String name);

    /**
     * Метод поиска по идентификатору
     * @param id идентификатор
     * @return возвращает найденный чат
     */
    ChatModel findById(int id);

    /**
     * Метод удаления по идентификатору
     * @param id идентификатор
     * @return Возвращает результат удаления
     */
    Long deleteById(int id);
}
