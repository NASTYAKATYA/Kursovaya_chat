package ru.mirea.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.chat.model.UserModel;
/**
 * Интерфейс для получения информации о пользователях из таблиц базы данных
 * @author Бирюкова Екатерина
 */
@Repository
public interface IUserRepository extends JpaRepository<UserModel, Integer> {
    /**
     * Метод поиска пользователя по имени
     * @param username Имя пользователя
     * @return Возвращает пользователя
     */
    UserModel findByUsername(String username);
}
