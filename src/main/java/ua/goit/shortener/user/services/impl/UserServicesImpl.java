package ua.goit.shortener.user.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UserRepository;
import ua.goit.shortener.user.services.UserServices;

@Service
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;

    @Autowired
    public UserServicesImpl(UserRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
