package ua.goit.shortener.user.services;

import org.springframework.stereotype.Service;
import ua.goit.shortener.user.entity.User;

import java.util.Optional;

@Service
public interface UserServices {
    void saveUser(User user);

    void deleteUser(Long id);
    Optional<User> findUser(Long id);
    User saveUser(User user);
    Optional<User> findByEmail(String email);
}