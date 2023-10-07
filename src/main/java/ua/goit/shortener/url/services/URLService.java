package ua.goit.shortener.url.services;

import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;

@Service
public interface URLService {
    String createShortURL(String originalURL);
//створення короткого url
    boolean isValidURL(String shortURL);
    //перевірка на валідність
    UrlDTO getURLInfo(String shortURL);
    // інфо по url
    void incrementClickCount(String shortURL);
    //лічильник переходів
}
