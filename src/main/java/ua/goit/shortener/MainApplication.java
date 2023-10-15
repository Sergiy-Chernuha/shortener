package ua.goit.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ua.goit.shortener.url.services.impl.URLServiceImpl;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.services.impl.UserServicesImpl;

@SpringBootApplication
public class MainApplication {

//CLASS ONLY FOR TESTING!!!!
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);

        UserServicesImpl userServices = context.getBean(UserServicesImpl.class);
        User user = new User();
        user.setNickName("user2");
        user.setPassword("1234567");
        userServices.saveUser(user);

        URLServiceImpl urlService = context.getBean(URLServiceImpl.class);

        String originalURL = "https://www.i.ua/";
        Long userId = 1L;

        String shortURL = urlService.saveShortURL(userId, originalURL);
        System.out.println("Short URL: " + shortURL);
    }
}
