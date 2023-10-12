package ua.goit.shortener.url.services;

import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;

@Service
public interface URLService {
    boolean isValidURL(String originalURL);
    //перевірка на валідність оригінального url
    String createShortURL(String originalURL);
    //створення короткого url
    boolean isValidShortURL(String shortURL);

    UrlDTO getURLInfo(String shortURL);

    //перевірка на валідність короткого url
    void incrementClickCount(String shortURL);
    //лічильник переходів
    String getOriginalURL(String shortUrl);
    //отримання оригінального юрл
    String saveShortURL(Long userId, String originalURL);

    boolean updateShortURL(String shortURL);
    //збереження у бд
}
