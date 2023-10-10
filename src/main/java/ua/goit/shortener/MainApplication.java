package ua.goit.shortener;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

        UserServicesImpl userServices = context.getBean(UserServicesImpl.class);
        User user = new User();
        user.setNickName("user2");
        user.setPassword("1234567");
        userServices.saveUser(user);

        URLServiceImpl urlService = context.getBean(URLServiceImpl.class);

        String originalURL = "https://github.com/Sergiy-Chernuha/shortener/tree/unique_url_yuz";
        Long userId = 2L;

        String shortURL = urlService.saveShortURL(userId, originalURL);
        System.out.println("Short URL: " + shortURL);

//        //редірект
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpGet httpGet = new HttpGet(shortURL);
//            httpClient.execute(httpGet);
//            System.out.println("Redirected to: " + httpGet.getURI());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
