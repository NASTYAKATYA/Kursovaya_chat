package ru.mirea.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.chat.model.UserModel;
import ru.mirea.chat.repository.IUserRepository;
/**
 * Класс-сервис для передачи данных из таблицы БД с пользователями в контроллер
 * @author Бирюкова Екатерина
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с пользователями
     */
    private IUserRepository iUserRepository;
    /**
     * Реализация кодера паролей, который использует функцию сильного хэширования BCrypt
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Конструктор присваивает значение для объекта интерфейса-репозитория
     * @param iUserRepository  Интерфейс-репозиторий получающий данные из таблицы БД с пользователями
     */
    @Autowired
    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    /**
     * Метод получает пользователя из БД по имени пользователя
     * @param username Имя искомого пользователя
     * @return Возвращает искомого пользователя
     * @throws UsernameNotFoundException Выбрасывается, если реализация UserDetailsService не может найти пользователя по его имени пользователя
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findByUsername(username);
    }

    /**
     * Метод создает нового пользователя
     * @param username Имя пользователя
     * @param password Пароль пользователя
     */
    public void createUser(String username, String password) {
        UserModel u = new UserModel();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        iUserRepository.save(u);
    }
}
