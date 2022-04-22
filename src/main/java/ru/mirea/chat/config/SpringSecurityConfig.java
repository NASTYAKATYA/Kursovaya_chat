package ru.mirea.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.mirea.chat.service.UserService;
/**
 * Класс-конфигурация позволяет настраивать параметры безопасности путем переопределения методов
 * @author Бирюкова Екатерина
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Класс-сервис для передачи данных из таблицы БД с пользователями в контроллер
     */
    private final UserService userService;
    /**
     * Конструктор устанавливающий userService
     * @param userService Класс-сервис для передачи данных из таблицы БД с пользователями в контроллер
     */
    public SpringSecurityConfig(UserService userService) {
        this.userService = userService;
    }
    /**
     * Метод настраивает доступ к различным ресурсам сайта
     * @param auth Позволяет легко создавать аутентификацию в памяти, аутентификацию LDAP, аутентификацию на основе JDBC, добавлять службы UserDetailsService и добавлять службы AuthenticationProvider
     * @throws Exception Используется для обработки исключений программы
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    /**
     * Метод настраивает доступ к различным ресурсам сайта
     * @param http Позволяет настраивать веб-безопасность для определенных http-запросов
     * @throws Exception Используется для обработки исключений программы
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .cors()
            .disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .anyRequest().authenticated();
    }
}
