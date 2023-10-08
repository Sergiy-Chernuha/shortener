package ua.goit.shortener.url.services.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.services.URLService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Random;
import java.net.URL;

@Service
public class URLSvc implements URLService {
    private final OkHttpClient httpClient = new OkHttpClient();

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
    public String createShortURL(String originalURL) {
        // генерація посилання
        String prefix = "https://shorter/t3/";
        int randomLength = 6 + new Random().nextInt(3); // довжина від 6 до 8 символів
        String randomString = generateRandomString(randomLength);

        return prefix + randomString;
    }


    @Override
    public boolean isValidShortURL(String shortURL) {
        try {
            new URL(shortURL).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException exception) {
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
