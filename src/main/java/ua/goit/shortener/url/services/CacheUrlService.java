package ua.goit.shortener.url.services;

import org.springframework.stereotype.Service;

@Service
public interface CacheUrlService {
    String getOriginalURL(String shortURL);
}
