package ua.goit.shortener.url.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.repositories.URLRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShortenedUrlService {
    private final URLRepository urlRepository;
    @Autowired
    public ShortenedUrlService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<URL> getAllURLs() {
        return urlRepository.findAll();
    }

    public Optional<URL> getURLByShortURL(String shortURL) {
        return urlRepository.findById(shortURL);
    }

    public URL createURL(URL url) {
        return urlRepository.save(url);
    }

    public void deleteURL(String shortURL) {
        urlRepository.deleteById(shortURL);
    }

    public URL updateURL(URL url) {
        Optional<URL> existingURL = urlRepository.findById(url.getShortURL());
        if (existingURL.isPresent()) {
            URL updatedURL = existingURL.get();
            updatedURL.setLongURL(url.getLongURL());
            updatedURL.setCreateDate(url.getCreateDate());

            return urlRepository.save(updatedURL);
        } else {
            throw new IllegalArgumentException("Посилання не знайдено: " + url.getShortURL());
        }
    }
}
