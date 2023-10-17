package ua.goit.shortener.url.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.services.UserServices;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class URLServiceImpl implements URLService {
    private final UserServices userServices;
    private final CrudUrlServiceImpl crudUrlService;

    @Autowired
    public URLServiceImpl(UserServices userServices, CrudUrlServiceImpl crudUrlService) {
        this.userServices = userServices;
        this.crudUrlService = crudUrlService;
    }

    @Override
    public String saveShortURL(Long userId, String originalURL) {
        String shortURL = createShortURL(originalURL);
        URL url = new URL();

        url.setShortURL(shortURL);
        url.setLongURL(originalURL);
        url.setCreateDate(new Date()); //дата створення
        url.setClickCount(0); // кількість переходів
        User user = userServices.findUser(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found for userId: " + userId));
        // Отримати користувача за ідентифікатором
        url.setUser(user); // Призначити користувача URL
        url.setExpiryShortURL(); // Встановити термін придатності URL
        crudUrlService.saveURL(url);

        return shortURL;
    }

    @Override
    public String createShortURL(String originalURL) {
        // генерація посилання
        String prefix = "shorter/t3/";

        while (true) {
            int randomLength = 6 + new Random().nextInt(3); // довжина від 6 до 8 символів
            String randomString = generateRandomString(randomLength);
            String shortURL = prefix + randomString;

            if (isShortUrlUnique(shortURL))
                return shortURL;
        }
    }

    @Override
    public boolean isValidURL(String checkedURL) {
        try {
            new java.net.URL(checkedURL).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException exception) {
            return false;
        }
    }

    @Override
    public Boolean isActiveShortURL(URL checkedShortUrl) {

        if (checkedShortUrl == null) {
            return false;
        }

        Date expiryDate = checkedShortUrl.getExpiryDate();
        Date currentDate = new Date();

        if (expiryDate == null) {
            return false;
        }

        return expiryDate.after(currentDate);
    }

    @Override
    public void incrementClickCount(String shortURL) {
        Optional<URL> urlByShortURL = crudUrlService.getURLByShortURL(shortURL);

        if (urlByShortURL.isPresent()) {
            URL url = urlByShortURL.get();
            Integer clickCount = url.getClickCount();
            clickCount++;
            url.setClickCount(clickCount);
            crudUrlService.updateURL(url);
        }
    }

    @Override
    public String getOriginalURL(String shortURL) {
        Optional<URL> urls = crudUrlService.getURLByShortURL(shortURL);

        if (urls.isPresent() && isActiveShortURL(urls.get())) {
            incrementClickCount(shortURL);

            return urls.get().getLongURL();
        } else {
            return null;
        }
    }

    //генератор строки
    public String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    //перевірка, чи існує short URL в БД
    public boolean isShortUrlUnique(String url) {
        Optional<URL> existingURLs = crudUrlService.getURLByShortURL(url);
        return existingURLs.isEmpty();
    }

    @Override
    public UrlDTO getURLInfo(String shortURL) {
        Optional<URL> url = crudUrlService.getURLByShortURL(shortURL);

        if (url.isPresent()) {
            return mapToDTO(url.get());
        } else {
            return null;
        }
    }

    @Override
    public boolean updateShortURL(String shortURL, String newOriginUrl) {
        Optional<URL> urls = crudUrlService.getURLByShortURL(shortURL);

        if (urls.isPresent()) {
            URL updatedURL = urls.get();

            updatedURL.setLongURL(newOriginUrl);
            crudUrlService.updateURL(updatedURL);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public UrlDTO mapToDTO(URL url) {
        UrlDTO dto = new UrlDTO();
        dto.setShortURL(url.getShortURL());
        dto.setOriginalURL(url.getLongURL());
        dto.setCreateDate(url.getCreateDate());
        dto.setExpiryDate(url.getExpiryDate());
        dto.setClickCount(url.getClickCount());
        return dto;
    }
}
