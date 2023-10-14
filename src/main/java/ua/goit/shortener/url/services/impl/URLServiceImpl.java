package ua.goit.shortener.url.services.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.repositories.URLRepository;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UsersRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class URLServiceImpl implements URLService {
    private final OkHttpClient httpClient = new OkHttpClient();
    private final URLRepository urlRepository;
    private final UsersRepository usersRepository;
    private final CrudUrlServiceImpl crudUrlService;

    @Autowired
    public URLServiceImpl(URLRepository urlRepository, UsersRepository usersRepository, CrudUrlServiceImpl crudUrlService) {
        this.urlRepository = urlRepository;
        this.usersRepository = usersRepository;
        this.crudUrlService = crudUrlService;
    }

    @Override
    public boolean isValidURL(String originalURL) {
        Request request = new Request.Builder()
                .url(originalURL)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            // якщо код 200 то все ок
            return response.isSuccessful();
        } catch (IOException e) {
            // помилка при виконанні , URL недоступний
            return false;
        }
    }

    @Override
    public String saveShortURL(Long userId, String originalURL) {
        String shortURL = createShortURL(originalURL);
        URL url = new URL();

        url.setShortURL(shortURL);
        url.setLongURL(originalURL);
        url.setCreateDate(new Date()); //дата створення
        url.setClickCount(0); // кількість переходів
        User user = usersRepository.getOne(String.valueOf(userId)); // Отримати користувача за ідентифікатором
        url.setUser(user); // Призначити користувача URL
        url.setExpiryShortURL(); // Встановити термін придатності URL
        urlRepository.save(url);

        return shortURL;
    }

    @Override
    public String createShortURL(String originalURL) {
        // генерація посилання
        String prefix = "https://shorter/t3/";

        while (true) {
            int randomLength = 6 + new Random().nextInt(3); // довжина від 6 до 8 символів
            String randomString = generateRandomString(randomLength);
            String shortURL = prefix + randomString;

            if (isShortUrlUnique(shortURL))
                return shortURL;
        }
    }

    @Override
    public boolean isValidShortURL(String saveShortURL) {
        try {
            new java.net.URL(saveShortURL).toURI();

//            new java.net.URL(shortURL).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException exception) {

            return false;
        }
    }


    @Override
    public Optional<String> getShortURLWithCheckExpiry(String shortURL) {
        URL url = urlRepository.findByShortURL(shortURL);
        if (url == null) {
            return Optional.empty();
        }

        Date expiryDate = url.getExpiryDate();
        Date currentDate = new Date();

        if (expiryDate == null) {
            return Optional.empty();
        }

        if (currentDate.after(expiryDate)) {
            return Optional.empty();
        } else {
            return Optional.of(url.getLongURL());
        }
    }

    @Override
    public UrlDTO getURLInfo(String shortURL) {
        return null;
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
        List<URL> urls = urlRepository.findByShortURLContaining(shortURL);

        if (!urls.isEmpty()) {
            URL findOriginalUrl = urls.get(0); // перший об'єкт зі списку
            return findOriginalUrl.getLongURL();
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
        List<URL> existingURLs = urlRepository.findByShortURLContaining(url);
        return existingURLs.isEmpty();
    }
}
