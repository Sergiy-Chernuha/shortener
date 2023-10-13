//package ua.goit.shortener;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import ua.goit.shortener.url.dto.UrlDTO;
//import ua.goit.shortener.url.services.impl.URLServiceImpl;
//import ua.goit.shortener.user.entity.User;
//import ua.goit.shortener.user.services.impl.UserServicesImpl;
//
//@SpringBootApplication
//public class MainApplication {
//
////CLASS ONLY FOR TESTING!!!!
//    public static void main(String[] args) {
//        ApplicationContext context = SpringApplication.run(MainApplication.class, args);
//
//        UserServicesImpl userServices = context.getBean(UserServicesImpl.class);
//        User user = new User();
//        user.setNickName("user8");
//        user.setPassword("1234567");
//        userServices.saveUser(user);
//
//        URLServiceImpl urlService = context.getBean(URLServiceImpl.class);
//
//        String originalURL = "https://github.com/Sergiy-Chernuha/shortener/tree/unique_url_yuz";
//        Long userId = 3L;
//
//        String shortURL = urlService.saveShortURL(userId, originalURL);
//        System.out.println("Short URL: " + shortURL);
//
//        String getOriginal = String.valueOf(urlService.getOriginalURL(shortURL));
//        System.out.println(getOriginal);
//
//        UrlDTO info = urlService.getURLInfo(shortURL);
//        System.out.println(info);
//
//        String newURL = "https://shorter/t3/9JmIJz";
//        String update = String.valueOf(urlService.updateShortURL(newURL));
//        System.out.println(update);
//
//        UrlDTO info1 = urlService.getURLInfo(newURL);
//        System.out.println(info1);
//    }
//}