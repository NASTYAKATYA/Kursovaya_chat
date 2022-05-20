package ru.mirea.chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.chat.model.UserModel;
import ru.mirea.chat.repository.IUserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private IUserRepository iur;
    @Captor
    private ArgumentCaptor<UserModel> captor;

    private UserModel user1, user2, user3;
    @BeforeEach
    void setUp() {
        user1 = new UserModel();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("password");

        user2 = new UserModel();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setPassword("password");

        user3 = new UserModel();
        user3.setId(3);
        user3.setUsername("user3");
        user3.setPassword("password");
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(iur.findByUsername("user3")).thenReturn(user3);
        assertEquals(user3, iur.findByUsername("user3"));
    }

    @Test
    void createUser() {
        String username = "username", password = "password";

        userService.createUser(username, password);
        Mockito.verify(iur).save(captor.capture());
        UserModel captured = captor.getValue();
        assertEquals("username", captured.getUsername());
    }

    @Test
    void getAll() {
        Mockito.when(iur.findAll()).thenReturn(List.of(user1, user2, user3));
        assertEquals(List.of(user1, user2, user3), iur.findAll());
    }

}