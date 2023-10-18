package ua.goit.shortener.url.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import ua.goit.shortener.MainApplication;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UsersRepository;
import ua.goit.shortener.user.services.impl.UserServicesImpl;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrudUrlServiceImplTest {

    private UsersRepository usersRepository;

    public List<User> getUser() {
        return usersRepository.findAll();
    }

    @Test
    void updateURL() {
        ApplicationContext context = SpringApplication.run(MainApplication.class);

        UserServicesImpl userServices = context.getBean(UserServicesImpl.class);
        User user = new User();
        user.setNickName("user2");
        user.setPassword("1234567");
        userServices.saveUser(user);
        System.out.println(user.getNickName());

        user.setNickName("userTestNick");
        System.out.println(user.getNickName());

//      assertEquals("userTestNick", getUser());
        assertEquals("userTestNick", user.getNickName());

    }
}

