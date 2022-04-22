package ru.mirea.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mirea.chat.model.UserModel;
import ru.mirea.chat.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Данный класс является основным контроллером
 * @author Бирюкова Екатерина
 */

@Controller
public class MainController {
    /**
     * Класс-сервис для передачи данных из таблицы БД с пользователями в контроллер
     */
    private final UserService userService;

    /**
     *
     * Конструктор для основного контроллера
     * @param userService Сервис для пользователей
     */

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод, принимающий GET запросы /
     * @param authentication Объект, идентифицирующий пользователя, обратившегося к методу
     * @param model Объект, предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает основную страницу чата или страницу для авторизации (в случае, когда не удалось идентифицировать пользователя)
     */

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        if (authentication == null)
            return "redirect:/login";
        model.addAttribute("username", authentication.getName());
        return "chat";
    }

    /**
     * Метод, принимающий GET запросы /login
     * @return Возвращает страницу с авторизацией
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Метод, принимающий POST запросы /login для авторизации пользователей
     * @param request Объект, содержащий запрос, поступивший от пользователя
     * @param username Имя пользователя
     * @param password Пароль пользователя
     * @param model Объект, предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу чата, если пользователь был зарегистрирован, иначе страницу авторизации
     */
    @PostMapping("/login")
    public String loginIn(HttpServletRequest request, @RequestParam String username, @RequestParam String password, Model model) {
        UserModel user = (UserModel) userService.loadUserByUsername(username);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword()))
            {
                authWithHttpServletRequest(request, username, password);
                return "redirect:/";
            }
        }
        model.addAttribute("status", "wrong_login_or_password");
        return "login";
    }

    /**
     * Метод, принимающий GET запросы /logout
     * @return Возвращает страницу с авторизацией
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    /**
     * Метод, принимающий GET запросы /signup
     * @return Возвращает страницу с регистрацией
     */
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    /**
     * Метод принимающий POST запросы /signup для регистрации пользователей
     * @param request Объект, содержащий запрос, поступивший от пользователя
     * @param username Имя пользователя
     * @param password Пароль пользователя
     * @param model Объект, предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу регистрации, если имя пользователя уже существует, иначе главную страницу
     */
    @PostMapping("/signup")
    public String createUser(HttpServletRequest request, @RequestParam String username, @RequestParam String password, Model model) {
        if (userService.loadUserByUsername(username) != null) {
            model.addAttribute("status", "user_exists");
            return "signup";
        }
        else {
            userService.createUser(username,password);
            authWithHttpServletRequest(request, username, password);
            return "redirect:/";
        }
    }
    /**
     * Метод для проверки существования пользователя
     * @param request Объект содержащий запрос, поступивший от пользователя
     * @param username Имя пользователя
     * @param password Пароль пользователя
     */
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }
}
