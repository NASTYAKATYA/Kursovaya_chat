package ru.mirea.chat.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
/**
 * Класс модели представления для пользователя
 * @author Бирюкова Екатерина
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class UserModel implements UserDetails {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    /**
     * Имя пользователя
     */
    @Column(name="username")
    String username;
    /**
     * Пароль пользователя
     */
    @Column(name="password")
    String password;

    /**
     * Переопределенный метод для представления полномочий объекту аутентификации
     * @return Представление полномочий или null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
