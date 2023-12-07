package ua.goit.shortener.url.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.user.services.UserServices;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@WebMvcTest(CacheUrlServiceImpl.class)
class CacheUrlServiceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private URLService urlService;

    @MockBean
    private Map<String, URL> urlCache;

    @MockBean
    @Qualifier("V1")
    private CacheUrlServiceImpl cacheUrlService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        urlCache = new HashMap<>();
        cacheUrlService = new CacheUrlServiceImpl(urlService, urlCache);
    }

    @Test
    void getOriginalURL_CacheHit_ActiveURL() {
        String shortURL = "shorter/t3/liza1234";
        URL activeUrlFromDB = new URL("https://www.google.com", shortURL, true);

        urlCache.put(shortURL, activeUrlFromDB);
        when(urlService.isActiveShortURL(activeUrlFromDB)).thenReturn(true);

        String result = cacheUrlService.getOriginalURL(shortURL);

        assertEquals("https://www.google.com", result);
        verify(urlService, times(1)).incrementClickCount(activeUrlFromDB);
    }

    @Test
    void getOriginalURL_CacheHit_InactiveURL() {
        String shortURL = "shorter/t3/liza1234";
        URL inactiveUrlFromDB = new URL("https://www.google.com", shortURL, false);

        urlCache.put(shortURL, inactiveUrlFromDB);
        when(urlService.isActiveShortURL(inactiveUrlFromDB)).thenReturn(false);

        String result = cacheUrlService.getOriginalURL(shortURL);

        assertNull(result);
        verify(urlService, never()).incrementClickCount(inactiveUrlFromDB);
    }

    @Test
    void getOriginalURL_CacheMiss_ActiveURL() {
        String shortURL = "shorter/t3/liza1234";
        URL activeUrlFromDB = new URL("https://www.google.com", shortURL, true);

        when(urlService.getActiveURL(shortURL)).thenReturn(activeUrlFromDB);
        when(urlService.isActiveShortURL(activeUrlFromDB)).thenReturn(true);

        String result = cacheUrlService.getOriginalURL(shortURL);

        assertEquals("https://www.google.com", result);
        verify(urlService, times(1)).incrementClickCount(activeUrlFromDB);
        verify(urlService, times(1)).getActiveURL(shortURL);
    }

    @Test
    void getOriginalURL_CacheMiss_InactiveURL() {
        String shortURL = "shorter/t3/liza1234";
        URL inactiveUrlFromDB = new URL("https://www.google.com", shortURL, false);

        when(urlService.getActiveURL(shortURL)).thenReturn(inactiveUrlFromDB);
        when(urlService.isActiveShortURL(inactiveUrlFromDB)).thenReturn(false);

        String result = cacheUrlService.getOriginalURL(shortURL);

        assertNull(result);
        verify(urlService, times(1)).getActiveURL(shortURL);
        verify(urlService, never()).incrementClickCount(inactiveUrlFromDB);
    }

    @Test
    void getOriginalURL_CacheMiss_NullFromDB() {
        String shortURL = "shorter/t3/liza1234";

        when(urlService.getActiveURL(shortURL)).thenReturn(null);

        String result = cacheUrlService.getOriginalURL(shortURL);

        assertNull(result);
        verify(urlService, times(1)).getActiveURL(shortURL);
        verify(urlService, never()).isActiveShortURL(any());
        verify(urlService, never()).incrementClickCount(any());
    }
}