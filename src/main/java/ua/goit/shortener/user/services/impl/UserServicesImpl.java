package ua.goit.shortener.user.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UserRepository;
import ua.goit.shortener.user.services.UserServices;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {
    private final UserRepository usersRepository;

    @Autowired
    public UserServicesImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
    public Optional<User> findUser(Long id) {
        return usersRepository.findById(id);
    }
    @Override
    public User saveUser(User user) {
        usersRepository.save(user);
        return user;
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
