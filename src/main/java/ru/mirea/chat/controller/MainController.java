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

@Controller
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        if (authentication == null)
            return "redirect:/login";
        model.addAttribute("username", authentication.getName());
        return "chat";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
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
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
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
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }
}
