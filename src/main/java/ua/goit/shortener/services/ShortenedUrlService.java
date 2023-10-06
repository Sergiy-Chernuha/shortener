package ua.goit.shortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.entities.ShortenedUrl;
import ua.goit.shortener.repositories.ShortenedUrlRepository;


import java.util.Date;

@Service
public class ShortenedUrlService {
    private final ShortenedUrlRepository shortenedUrlRepository;
    @Autowired
    public ShortenedUrlService(ShortenedUrlRepository shortenedUrlRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

    public ShortenedUrl createShortenedUrl(ShortenedUrl shortenedUrl) {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(originalUrl);
        shortenedUrl.setUser(user);
        shortenedUrl.setExpirationDate(calculateExpirationDate());
        shortenedUrl.setCreationDate(new Date());
        shortenedUrl.setCount(0);
        String shortUrl = generateShortUrl();
        shortenedUrl.setShortUrl(shortUrl);

        return originalUrl;
    }

    private String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder();
        return shortUrl.toString();
    }

    private Date calculateExpirationDate() {

        return new Date();
    }
}
