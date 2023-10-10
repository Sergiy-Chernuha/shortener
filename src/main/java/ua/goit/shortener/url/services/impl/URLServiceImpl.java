package ua.goit.shortener.url.services.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.repositories.URLRepository;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UsersRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class URLServiceImpl implements URLService {
    private final OkHttpClient httpClient = new OkHttpClient();
    private final URLRepository urlRepository;

    private final UsersRepository usersRepository;

    @Autowired
    public URLServiceImpl(URLRepository urlRepository, UsersRepository usersRepository) {
        this.urlRepository = urlRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean isValidURL(String originalURL) {
        Request request = new Request.Builder()
                .url(originalURL)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            // якщо код 200 то все ок
            return response.isSuccessful();
        } catch (IOException e) {
            // помилка при виконанні , URL недоступний
            return false;
        }
    }

    @Override
    public String saveShortURL(Long userId, String originalURL) {
        String shortURL = createShortURL(originalURL);
        URL url = new URL();
        url.setShortURL(shortURL);
        url.setLongURL(originalURL);
        url.setCreateDate(new Date()); //дата створення
        url.setClicks(0); // кількість переходів
        User user = usersRepository.getOne(String.valueOf(userId)); // Отримати користувача за ідентифікатором
        url.setUser(user); // Призначити користувача URL
        urlRepository.save(url);

        return shortURL;
    }
    @Override
    public String createShortURL(String originalURL) {
        // генерація посилання
        String prefix = "https://shorter/t3/";
        int randomLength = 6 + new Random().nextInt(3); // довжина від 6 до 8 символів
        String randomString = generateRandomString(randomLength);

        return prefix + randomString;
    }

    @Override
    public boolean isValidShortURL(String saveShortURL) {
        try {
            new java.net.URL(saveShortURL).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException exception) {
            return false;
        }
    }

    @Override
    public void incrementClickCount(String shortURL) {

    }

    @Override
    public String getOriginalURL(String shortURL) {
        List<URL> urls = urlRepository.findByShortURLContaining(shortURL);
        if (!urls.isEmpty()) {
            URL findOriginalUrl = urls.get(0); // перший об'єкт зі списку
            return findOriginalUrl.getLongURL();
        } else {
            return null;
        }
    }



    //генератор строки
    // може його перенести в окремий клас????
    public String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            randomString.append(randomChar);
        }
        return randomString.toString();
    }
}