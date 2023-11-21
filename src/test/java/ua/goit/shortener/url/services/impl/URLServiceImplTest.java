package ua.goit.shortener.url.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.goit.shortener.url.controller.UrlController;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.user.entity.User;

import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.CrudUrlService;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.user.services.UserServices;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    public void testSaveShortURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        User user = new User();
        user.setId(1L);
        String originalURL = "https://www.i.ua/";

        when(userServices.findUser(1L)).thenReturn(Optional.of(user));

        String shortURL = urlService.saveShortURL(1L, originalURL);

        verify(userServices, times(1)).findUser(1L);
        verify(crudUrlService, times(1)).saveURL(any(URL.class));

        assertEquals("shorter/t3/", shortURL.substring(0, 11));
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
    public void testGenerateRandomString() {
        URLServiceImpl urlService = new URLServiceImpl(null, null);
        int length = 8;

        String randomString = urlService.generateRandomString(length);

        assertEquals(length, randomString.length());
    }

    @Test
    public void testIsShortUrlUniqueWithUniqueURL() {
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(null, crudUrlService);

        when(crudUrlService.getURLByShortURL("shortURL")).thenReturn(Optional.empty());

        boolean isUnique = urlService.isShortUrlUnique("shortURL");

        verify(crudUrlService, times(1)).getURLByShortURL("shortURL");

        assertTrue(isUnique);
    }

    @Test
    public void testGetURLInfo() {
        String shortURL = "shorter/t3/abc123";
        URL url = new URL();
        url.setShortURL(shortURL);
        url.setLongURL("https://www.i.ua/");
        url.setCreateDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + 3600000));
        url.setClickCount(0);

        CrudUrlServiceImpl mockCrudUrlService;
        mockCrudUrlService = mock(CrudUrlServiceImpl.class);
        when(mockCrudUrlService.getURLByShortURL(shortURL)).thenReturn(Optional.of(url));

        URLServiceImpl urlService;
        urlService = new URLServiceImpl(null, mockCrudUrlService);
        UrlDTO urlInfo = urlService.getURLInfo(shortURL);

        assertNotNull(urlInfo);
        assertEquals(shortURL, urlInfo.getShortURL());
        assertEquals(url.getLongURL(), urlInfo.getOriginalURL());
        assertEquals(url.getCreateDate(), urlInfo.getCreateDate());
        assertEquals(url.getExpiryDate(), urlInfo.getExpiryDate());
        assertEquals(url.getClickCount(), urlInfo.getClickCount());

        verify(mockCrudUrlService, times(1)).getURLByShortURL(shortURL);
    }

    @Test
    public void testUpdateShortURL() {
        // Приклад даних для тесту
        String shortURL = "shorter/t3/abc123";
        String newOriginalURL = "https://www.youtube.com/";
        URL url = new URL();
        url.setShortURL(shortURL);
        url.setLongURL("https://www.i.ua/");
        url.setCreateDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + 3600000)); // Через годину
        url.setClickCount(0);

        CrudUrlServiceImpl mockCrudUrlService;
        mockCrudUrlService = mock(CrudUrlServiceImpl.class);
        when(mockCrudUrlService.getURLByShortURL(shortURL)).thenReturn(Optional.of(url));

        URLServiceImpl urlService;
        urlService = new URLServiceImpl(null, mockCrudUrlService);
        boolean result = urlService.updateShortURL(shortURL, newOriginalURL);

        assertTrue(result);

        verify(mockCrudUrlService, times(1)).getURLByShortURL(shortURL);

        assertEquals(newOriginalURL, url.getLongURL());
    }

    @Test
    public void testMapToDTO() {
        URLServiceImpl urlService = new URLServiceImpl(null, null);
        URL url = new URL();
        url.setShortURL("shortURL");
        url.setLongURL("longURL");

        UrlDTO dto = urlService.mapToDTO(url);

        assertEquals("shortURL", dto.getShortURL());
        assertEquals("longURL", dto.getOriginalURL());
    }
}
