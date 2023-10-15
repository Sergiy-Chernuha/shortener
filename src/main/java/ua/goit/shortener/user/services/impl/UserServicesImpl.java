package ua.goit.shortener.user.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UsersRepository;
import ua.goit.shortener.user.services.UserServices;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {
    private final UsersRepository usersRepository;

    @Autowired
    public UserServicesImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<User> findUser(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        usersRepository.save(user);
    }
}
