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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private IUserRepository iUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findByUsername(username);
    }
    public void createUser(String username, String password) {
        UserModel u = new UserModel();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        iUserRepository.save(u);
    }
}
