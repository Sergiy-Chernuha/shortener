package ua.goit.shortener.url.services.impl;

import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.URLService;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class URLServiceImpl implements URLService {
    @Override
    public String createShortURL(String originalURL) {
        // генерація посилання
        String prefix = "https://shorter/t3/";
        int randomLength = 6 + new Random().nextInt(3); // довжина від 6 до 8 символів
        String randomString = generateRandomString(randomLength);

        // Створення URL
        URL url = new URL();
        url.setShortURL(prefix + randomString);
        url.setLongURL(originalURL);
        url.setCreateDate(new Date());
        url.setClicks(0);

        // Термін дії url
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2); // Встановлення терміну на 2 доби
        Date expiryDate = calendar.getTime();
        url.setExpiryDate(expiryDate);

        return url.getShortURL();
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
