package ua.goit.shortener.url.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.CrudUrlService;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.user.services.UserServices;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class URLServiceImplTest {

    @Mock
    private UserServices userServices;

    @Mock
    private CrudUrlService crudUrlService;

    private URLService urlService;

    @BeforeEach
    public void setUp() {
        urlService = new URLServiceImpl(userServices, crudUrlService);
    }

    @Test
    void saveShortURL() {
    }

    @Test
    public void testCreateShortURL() {
        String shortURL = urlService.createShortURL("https://www.example.com");

        assertNotNull(shortURL);
        assertTrue(shortURL.startsWith("shorter/t3/"));
    }

    @Test
    public void testCreateShortURLLength() {
        String shortURL1 = urlService.createShortURL("https://www.example.com");
        String shortURL2 = urlService.createShortURL("https://www.example.com");
        String shortURL3 = urlService.createShortURL("https://www.example.com");

        assertNotNull(shortURL1);
        assertNotNull(shortURL2);
        assertNotNull(shortURL3);

        assertNotEquals(shortURL1, shortURL2);
        assertNotEquals(shortURL1, shortURL3);
        assertNotEquals(shortURL2, shortURL3);
    }

    @Test
    public void testCreateShortURLForDifferentURLs() {
        String shortURL1 = urlService.createShortURL("https://www.example.com");
        String shortURL2 = urlService.createShortURL("https://www.example.org");
        String shortURL3 = urlService.createShortURL("https://www.example.net");

        assertNotNull(shortURL1);
        assertNotNull(shortURL2);
        assertNotNull(shortURL3);

        assertNotEquals(shortURL1, shortURL2);
        assertNotEquals(shortURL1, shortURL3);
        assertNotEquals(shortURL2, shortURL3);
    }

    @Test
    public void testIsValidURLWithValidHTTPURL() {
        boolean isValid = urlService.isValidURL("http://www.example.com");
        assertTrue(isValid);
    }

    @Test
    public void testIsValidURLWithValidHTTPSURL() {
        boolean isValid = urlService.isValidURL("https://www.example.com");
        assertTrue(isValid);
    }

    @Test
    public void testIsValidURLWithInvalidURL() {
        boolean isValid = urlService.isValidURL("this_is_not_a_valid_url");
        assertFalse(isValid);
    }

    @Test
    public void testIsValidURLWithNullURL() {
        boolean isValid = urlService.isValidURL(null);
        assertFalse(isValid);
    }

    @Test
    public void testIsActiveShortURLWithActiveURL() {
        URL activeURL = new URL();
        activeURL.setShortURL("shorter/t3/abc123");
        activeURL.setLongURL("https://www.example.com");
        activeURL.setCreateDate(new Date());
        activeURL.setExpiryShortURL();

        boolean isActive = urlService.isActiveShortURL(activeURL);

        assertTrue(isActive);
    }

    @Test
    public void testIsActiveShortURLWithExpiredURL() {
        URL expiredURL = new URL();
        expiredURL.setShortURL("shorter/t3/def456");
        expiredURL.setLongURL("https://www.example.com");
        expiredURL.setCreateDate(new Date());
        expiredURL.setExpiryDate(new Date(System.currentTimeMillis() - 86400000));

        boolean isActive = urlService.isActiveShortURL(expiredURL);

        assertFalse(isActive);
    }

    @Test
    public void testIsActiveShortURLWithNullURL() {
        boolean isActive = urlService.isActiveShortURL(null);
        assertFalse(isActive);
    }

    @Test
    void incrementClickCount() {
    }

    @Test
    void getActiveURL() {
    }

    @Test
    void generateRandomString() {
    }

    @Test
    void isShortUrlUnique() {
    }

    @Test
    void getURLInfo() {
    }

    @Test
    void updateShortURL() {
    }

    @Test
    void mapToDTO() {
    }
}
