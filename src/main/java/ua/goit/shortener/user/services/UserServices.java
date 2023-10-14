package ua.goit.shortener.user.services;

import org.springframework.stereotype.Service;
import ua.goit.shortener.user.entity.User;

@Service
public interface UserServices {
    void saveUser(User user);
}