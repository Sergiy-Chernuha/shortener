package ua.goit.shortener.url.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.repositories.URLRepository;
import ua.goit.shortener.url.services.CrudUrlService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("V1")
public class CrudUrlServiceImpl implements CrudUrlService {
    private final URLRepository urlRepository;

    @Autowired
    public CrudUrlServiceImpl(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public List<URL> getAllURLs() {
        return urlRepository.findAll();
    }

    @Override
    public Optional<URL> getURLByShortURL(String shortURL) {
        return urlRepository.findById(shortURL);
    }

    @Override
    public URL saveURL(URL url) {
        return urlRepository.save(url);
    }

    @Override
    public void deleteURL(String shortURL) {
        urlRepository.deleteById(shortURL);
    }

    @Override
    public URL updateURL(URL url) {
        Optional<URL> existingURL = urlRepository.findById(url.getShortURL());
        if (existingURL.isPresent()) {
            URL updatedURL = existingURL.get();
            updatedURL.setLongURL(url.getLongURL());
            updatedURL.setCreateDate(new Date());
            updatedURL.setExpiryShortURL();
            updatedURL.setClickCount(0);

            return urlRepository.save(updatedURL);
        } else {
            return null;
        }
    }

    @Override
    public List<URL> getAllURLsByUserId(Long userId) {
        List<URL> urls = urlRepository.findAllByUserId(userId);
        return urls;
    }
}
