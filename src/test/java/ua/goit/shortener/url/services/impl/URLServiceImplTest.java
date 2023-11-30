package ua.goit.shortener.url.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.CrudUrlService;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.services.UserServices;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@WebMvcTest(URLServiceImpl.class)
class URLServiceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CrudUrlService crudUrlService;
    @MockBean
    private UserServices userServices;
    @MockBean
    @Qualifier("V1")
    private URLServiceImpl urlService;

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

        assertTrue(shortURL.startsWith("shorter/t3/"));
        String actualSubstring = shortURL.substring("shorter/t3/".length());
        assertTrue(actualSubstring.length() >= 6 && actualSubstring.length() <= 8);
    }

    @Test
    public void testCreateShortURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);
        String originalURL = "https://www.google.com";

        String shortURL = urlService.createShortURL(originalURL);
        assertTrue(shortURL.startsWith("shorter/t3/"));

        String actualSubstring = shortURL.substring("shorter/t3/".length());
        assertTrue(actualSubstring.length() >= 6 && actualSubstring.length() <= 8);
    }

    @Test
    void testIsValidURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);
        String validURL = "https://www.google.com";
        String invalidURL = "not a valid URL";

        assertTrue(urlService.isValidURL(validURL));
        assertFalse(urlService.isValidURL(invalidURL));
    }

    @Test
    public void testIncrementClickCount() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        URL url = new URL();
        url.setClickCount(5);

        urlService.incrementClickCount(url);

        assertEquals(6, url.getClickCount());
        verify(crudUrlService, times(1)).saveURL(argThat(savedURL -> savedURL.getClickCount() == 6));

        verifyNoMoreInteractions(crudUrlService);
    }

    @Test
    public void testGetActiveURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        String shortURL = "shorter/t3/liza1234";
        URL activeURL = new URL();
        activeURL.setShortURL(shortURL);
        activeURL.setExpiryDate(new Date(System.currentTimeMillis() + 1000));

        when(crudUrlService.getURLByShortURL(shortURL)).thenReturn(Optional.of(activeURL));

        URL result = urlService.getActiveURL(shortURL);

        assertNotNull(result);
        assertEquals(shortURL, result.getShortURL());
        assertEquals(1, result.getClickCount());

        verify(crudUrlService, times(1)).getURLByShortURL(shortURL);
        verify(crudUrlService, times(1)).saveURL(activeURL);
        verifyNoMoreInteractions(crudUrlService);
    }

    @Test
    public void testIsActiveShortURLWithActiveURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        URL activeURL = new URL();
        activeURL.setShortURL("shorter/t3/liza1234");
        activeURL.setLongURL("https://www.google.com");
        activeURL.setCreateDate(new Date());
        activeURL.setExpiryShortURL();

        boolean isActive = urlService.isActiveShortURL(activeURL);

        assertTrue(isActive);

    }

    @Test
    public void testIsActiveShortURLWithExpiredURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        URL expiredURL = new URL();
        expiredURL.setShortURL("shorter/t3/liza1234");
        expiredURL.setLongURL("https://www.google.com");
        expiredURL.setCreateDate(new Date());
        expiredURL.setExpiryDate(new Date(System.currentTimeMillis() - 1000));

        boolean isActive = urlService.isActiveShortURL(expiredURL);

        assertFalse(isActive);
    }

    @Test
    public void testIsActiveShortURLWithNullURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        boolean isActive = urlService.isActiveShortURL(null);
        assertFalse(isActive);
    }


    @Test
    public void testGenerateRandomString() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);

        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        int length = 8;

        String randomString = urlService.generateRandomString(length);

        assertNotNull(randomString);
        assertEquals(length, randomString.length());
        assertFalse(randomString.contains(" "), "String should not contain spaces");
        assertFalse(randomString.contains("\n"), "String should not contain line breaks");
        assertFalse(randomString.contains("\r"), "String should not contain carriage returns");
    }
    @Test
    public void testIsShortUrlUniqueWithUniqueURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);
        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        String shortURL = "shorter/t3/uLiza123";

        when(crudUrlService.getURLByShortURL(shortURL)).thenReturn(Optional.empty());
        assertTrue(urlService.isShortUrlUnique(shortURL));
        verify(crudUrlService, times(1)).getURLByShortURL(shortURL);

        when(crudUrlService.getURLByShortURL(shortURL)).thenReturn(Optional.of(new URL()));
        assertFalse(urlService.isShortUrlUnique(shortURL));
        verify(crudUrlService, times(2)).getURLByShortURL(shortURL);
        verifyNoMoreInteractions(crudUrlService);
    }

    @Test
    public void testGetURLInfo() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);
        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        String shortURL = "shorter/t3/Liza123";
        URL url = new URL();
        url.setShortURL(shortURL);
        url.setLongURL("https://www.google.com");
        url.setCreateDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + 3600000));
        url.setClickCount(0);

        when(crudUrlService.getURLByShortURL(shortURL)).thenReturn(Optional.of(url));

        UrlDTO urlInfo = urlService.getURLInfo(shortURL);

        assertNotNull(urlInfo);
        assertEquals(shortURL, urlInfo.getShortURL());
        assertEquals(url.getLongURL(), urlInfo.getOriginalURL());
        assertEquals(url.getCreateDate(), urlInfo.getCreateDate());
        assertEquals(url.getExpiryDate(), urlInfo.getExpiryDate());
        assertEquals(url.getClickCount(), urlInfo.getClickCount());

        verify(crudUrlService, times(1)).getURLByShortURL(shortURL);
        verifyNoMoreInteractions(crudUrlService);
    }

    @Test
    public void testUpdateShortURL() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);
        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        String shortURL = "shorter/t3/Liza123";
        String newOriginalURL = "https://www.google.com";
        URL url = new URL();
        url.setShortURL(shortURL);
        url.setLongURL("https://github.com/Elliisiv");
        url.setCreateDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + 3600000));
        url.setClickCount(0);

        when(crudUrlService.getURLByShortURL(shortURL)).thenReturn(Optional.of(url));

        boolean result = urlService.updateShortURL(shortURL, newOriginalURL);

        assertTrue(result);

        verify(crudUrlService, times(1)).getURLByShortURL(shortURL);

        assertEquals(newOriginalURL, url.getLongURL());
    }

    @Test
    public void testMapToDTO() {
        UserServices userServices = mock(UserServices.class);
        CrudUrlServiceImpl crudUrlService = mock(CrudUrlServiceImpl.class);
        URLServiceImpl urlService = new URLServiceImpl(userServices, crudUrlService);

        URL url = new URL();
        String shortURL = "shorter/t3/Liza123";
        String longURL = "https://www.google.com";
        url.setShortURL(shortURL);
        url.setLongURL(longURL);

        UrlDTO dto = urlService.mapToDTO(url);

        assertEquals(shortURL, dto.getShortURL());
        assertEquals(longURL, dto.getOriginalURL());
    }
}
