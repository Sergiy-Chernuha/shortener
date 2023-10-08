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
    //перевірка на валідність короткого url
    UrlDTO getURLInfo(String shortURL);
    // інфо по url
    void incrementClickCount(String shortURL);
    //лічильник переходів

    String getOriginalURL(String shortUrl);
}
