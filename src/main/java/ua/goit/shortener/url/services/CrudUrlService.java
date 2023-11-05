package ua.goit.shortener.url.services;

import org.springframework.stereotype.Service;
import ua.goit.shortener.url.entity.URL;

import java.util.List;
import java.util.Optional;

@Service
public interface CrudUrlService {
    List<URL> getAllURLs();

    Optional<URL> getURLByShortURL(String shortURL);

    URL saveURL(URL url);

    void deleteURL(String shortURL);

    URL updateURL(URL url);

    List<URL> getAllURLsByUserId(Long userId);

}
