package ua.goit.shortener.url.services;

import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;

import java.util.Optional;

import java.io.IOException;

@Service
public interface URLService {
    boolean isValidURL(String originalURL) throws IOException, InterruptedException;
    //перевірка на валідність оригінального url
    String createShortURL(String originalURL);
    //створення короткого url
    boolean isValidShortURL(String shortURL);
    //перевірка на валідність короткого url
    UrlDTO getURLInfo(String shortURL);

    void incrementClickCount(String shortURL);
    //лічильник переходів
    String getOriginalURL(String shortUrl);
    //отримання оригінального юрл
    String saveShortURL(Long userId, String originalURL);

    boolean updateShortURL(String shortURL);
    //збереження у бд

    // перевірка терміну дії короткого URL
    Optional<String> getShortURLWithCheckExpiry(String shortURL);
}
