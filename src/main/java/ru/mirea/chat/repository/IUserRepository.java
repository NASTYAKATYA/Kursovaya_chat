package ru.mirea.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.chat.model.UserModel;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByUsername(String username);
}
