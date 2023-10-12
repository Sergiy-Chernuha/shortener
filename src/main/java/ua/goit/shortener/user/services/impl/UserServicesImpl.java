package ua.goit.shortener.user.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UsersRepository;

@Service
public class UserServicesImpl {
    private final UsersRepository usersRepository;

    @Autowired
    public UserServicesImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void saveUser(User user) {
        usersRepository.save(user);
    }
}
