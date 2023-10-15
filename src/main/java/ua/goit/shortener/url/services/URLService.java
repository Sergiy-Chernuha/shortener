package ua.goit.shortener.url.services;

import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;

@Service
public interface URLService {
    //перевірка на валідність оригінального url
    String createShortURL(String originalURL);

    //створення короткого url
    boolean isValidURL(String shortURL);

    //перевірка на валідність короткого url
    UrlDTO getURLInfo(String shortURL);

    void incrementClickCount(String shortURL);

    //лічильник переходів
    String getOriginalURL(String shortUrl);

    //отримання оригінального юрл
    String saveShortURL(Long userId, String originalURL);

    //збереження у бд
    boolean updateShortURL(String shortURL, String newOriginUrl);
    //оновлення короткогоURL і всих його параметрів

    // перевірка терміну дії короткого URL
    Boolean isActiveShortURL(URL checkedShortUrl);

    // парсинг ентіті в ДТО
    UrlDTO mapToDTO(URL url);
}
