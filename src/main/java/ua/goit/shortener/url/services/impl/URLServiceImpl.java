package ua.goit.shortener.url.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.repositories.URLRepository;
import ua.goit.shortener.url.services.URLService;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;


@Service
public class URLServiceImpl implements URLService {

    private final URLRepository urlRepository;

    @Autowired
    public URLServiceImpl(URLRepository urlRepository) {
        this.urlRepository = urlRepository; }

    @Override
    public String createShortURL(String originalURL) {
        // генерація посилання
        String prefix = "https://shorter/t3/";
        int randomLength = 6 + new Random().nextInt(3); // довжина від 6 до 8 символів
        String randomString = generateRandomString(randomLength);

        String shortURL =  prefix + randomString;

        // Перевірка, чи такий короткий URL вже існує в базі даних
        List<URL> existingURLs = urlRepository.findByShortURLContaining(shortURL);

        if (existingURLs.isEmpty()) {
            // Якщо короткий URL ще не існує, то використовуємо його
            return shortURL;
        } else {
            // Якщо короткий URL вже існує, генеруємо новий
            return createShortURL(originalURL);
        }
    }

    @Override
    public boolean isValidURL(String shortURL) {
        try {
            new java.net.URL(shortURL).toURI();
            return true;
        } catch (URISyntaxException exception) {
            return false;
        } catch (MalformedURLException exception) {
            return false;
        }
    }

    @Override
    public UrlDTO getURLInfo(String shortURL) {
        return null;
    }

    @Override
    public void incrementClickCount(String shortURL) {

    }

    //генератор строки
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
