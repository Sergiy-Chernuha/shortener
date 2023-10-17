package ua.goit.shortener.url.services.impl;

import org.junit.jupiter.api.Test;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.services.UserServices;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class URLServiceImplTest {

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
    void createShortURL() {
    }

    @Test
    void isValidURL() {
    }

    @Test
    void isActiveShortURL() {
    }

    @Test
    public void testIncrementClickCount() {
        CrudUrlServiceImpl mockCrudUrlService = mock(CrudUrlServiceImpl.class);
        URLServiceImpl urlService = new URLServiceImpl(null, mockCrudUrlService);

        URL url = new URL();
        url.setShortURL("shortURL");
        url.setClickCount(0);

        when(mockCrudUrlService.getURLByShortURL("shortURL")).thenReturn(Optional.of(url));

        urlService.incrementClickCount("shortURL");

        verify(mockCrudUrlService, times(1)).getURLByShortURL("shortURL");
        verify(mockCrudUrlService, times(1)).updateURL(url);
    }


    @Test
    public void testGetOriginalURLWithActiveURL() {
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(null, crudUrlService);

        URL url = new URL();
        url.setShortURL("shortURL");
        url.setLongURL("https://www.i.ua/");

        when(crudUrlService.getURLByShortURL("shortURL")).thenReturn(Optional.of(url));

        String originalURL = urlService.getOriginalURL("shortURL");

        verify(crudUrlService, times(1)).getURLByShortURL("shortURL");
    }

    @Test
    public void testGetOriginalURLWithExpiredURL() {
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(null, crudUrlService);

        URL url = new URL();
        url.setShortURL("shortURL");
        url.setLongURL("https://www.i.ua/");

        when(crudUrlService.getURLByShortURL("shortURL")).thenReturn(Optional.of(url));

        String originalURL = urlService.getOriginalURL("shortURL");

        verify(crudUrlService, times(1)).getURLByShortURL("shortURL");

        assertEquals(null, originalURL);
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
