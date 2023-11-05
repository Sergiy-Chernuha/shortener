package ua.goit.shortener.user.services;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Service;
import ua.goit.shortener.user.entity.User;

import java.util.Optional;
@Hidden
@Service
public interface UserServices {
    void deleteUser(Long id);
    Optional<User> findUser(Long id);
    User saveUser(User user);
    Optional<User> findByEmail(String email);
}