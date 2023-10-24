package ua.goit.shortener.url.services.impl;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.CacheUrlService;
import ua.goit.shortener.url.services.URLService;

import java.util.HashMap;
import java.util.Map;
@Hidden
@Service
@Qualifier("V1")
public class CacheUrlServiceImpl implements CacheUrlService {
    private final Map<String, URL> urlCache;
    private final URLService urlService;

    public CacheUrlServiceImpl(@Qualifier("V1") URLService urlService) {
        urlCache = new HashMap<>();
        this.urlService = urlService;
    }

    @Override
    public String getOriginalURL(String shortURL) {
        if (urlCache.containsKey(shortURL)) {
            URL checkedShortUrl = urlCache.get(shortURL);

            if (urlService.isActiveShortURL(checkedShortUrl)) {
                urlService.incrementClickCount(checkedShortUrl);

                return checkedShortUrl.getLongURL();
            } else {
                return null;
            }

        } else {
            URL activeUrlFromDB = urlService.getActiveURL(shortURL);

            if (activeUrlFromDB != null) {
                urlCache.put(shortURL, activeUrlFromDB);

                return activeUrlFromDB.getLongURL();
            } else {
                return null;
            }
        }
    }
}