package ua.goit.shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.url.services.impl.URLServiceImpl;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UsersRepository;
import ua.goit.shortener.user.services.UserServicesImpl;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);

//        UserServicesImpl userServices = context.getBean(UserServicesImpl.class);
//        User user = new User();
//        user.setNickName("user1");
//        user.setPassword("123456");
//        userServices.saveUser(user);

        URLServiceImpl urlService = context.getBean(URLServiceImpl.class);

        String originalURL = "https://docs.google.com/document/d/1CmvPkcy-fo49BqlTwnC_iYBhj5KSmItM2V9Ps6lLtnI/edit";
        Long userId = 1L;

        System.out.println(urlService.saveShortURL(userId, originalURL));
    }
}
