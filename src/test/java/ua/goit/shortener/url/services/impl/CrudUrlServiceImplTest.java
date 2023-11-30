package ua.goit.shortener.url.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.repositories.URLRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CrudUrlServiceImpl.class)
class CrudUrlServiceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private URLRepository urlRepository;
    @MockBean
    @Qualifier("V1")
    private CrudUrlServiceImpl crudUrlService;

    @BeforeEach
    public void setUp() {
        crudUrlService = new CrudUrlServiceImpl(urlRepository);
    }

    @Test
    public void testGetAllURLs() {
        URLRepository urlRepository = mock(URLRepository.class);

        CrudUrlServiceImpl crudUrlService = new CrudUrlServiceImpl(urlRepository);

        List<URL> allURL = new ArrayList<>();
        when(urlRepository.findAll()).thenReturn(allURL);

        List<URL> activeURL = crudUrlService.getAllURLs();

        assertEquals(allURL, activeURL);

        verify(urlRepository, times(1)).findAll();
    }

    @Test
    public void testGetURLByShortURL() {
        URLRepository urlRepository = mock(URLRepository.class);

        CrudUrlServiceImpl crudUrlService = new CrudUrlServiceImpl(urlRepository);

        String shortURL = "shorter/t3/Liza11";
        URL findURl = new URL();

        when(urlRepository.findById(shortURL)).thenReturn(Optional.of(findURl));

        Optional<URL> result = crudUrlService.getURLByShortURL(shortURL);

        assertTrue(result.isPresent());
        assertEquals(findURl, result.get());

        verify(urlRepository, times(1)).findById(shortURL);
    }

    @Test
    public void testSaveURL() {
        URLRepository urlRepository = mock(URLRepository.class);

        CrudUrlServiceImpl crudUrlService = new CrudUrlServiceImpl(urlRepository);

        URL urlToSave = new URL();
        when(urlRepository.save(urlToSave)).thenReturn(urlToSave);

        URL result = crudUrlService.saveURL(urlToSave);

        assertEquals(urlToSave, result);

        verify(urlRepository, times(1)).save(urlToSave);
    }

    @Test
    public void testDeleteURL() {
        URLRepository urlRepository = mock(URLRepository.class);

        CrudUrlServiceImpl crudUrlService = new CrudUrlServiceImpl(urlRepository);

        String shortURLToDelete = "shorter/t3/Liza11";

        crudUrlService.deleteURL(shortURLToDelete);

        verify(urlRepository, times(1)).deleteById(shortURLToDelete);
    }

    @Test
    public void testUpdateURL() {
        URLRepository urlRepository = mock(URLRepository.class);

        CrudUrlServiceImpl crudUrlService = new CrudUrlServiceImpl(urlRepository);

        URL existingUrl = new URL();
        existingUrl.setShortURL("shorter/t3/exist");
        existingUrl.setLongURL("https://github.com/Elliisiv");
        existingUrl.setCreateDate(new Date());
        existingUrl.setExpiryShortURL();
        existingUrl.setClickCount(0);

        URL updatedUrl = new URL();
        updatedUrl.setShortURL("shorter/t3/update");
        updatedUrl.setLongURL("https://www.google.com");
        updatedUrl.setCreateDate(new Date());
        updatedUrl.setExpiryShortURL();
        updatedUrl.setClickCount(0);

        when(urlRepository.findById(existingUrl.getShortURL())).thenReturn(Optional.of(existingUrl));
        when(urlRepository.save(existingUrl)).thenReturn(updatedUrl);

        URL result = crudUrlService.updateURL(existingUrl);

        assertEquals(updatedUrl.getLongURL(), result.getLongURL());

        verify(urlRepository, times(1)).findById(existingUrl.getShortURL());
        verify(urlRepository, times(1)).save(existingUrl);
    }

    @Test
    public void testGetAllURLsByUserId() {
        URLRepository urlRepository = mock(URLRepository.class);

        CrudUrlServiceImpl crudUrlService = new CrudUrlServiceImpl(urlRepository);

        Long userId = 1L;
        List<URL> findsUrls = new ArrayList<>();
        when(urlRepository.findAllByUserId(userId)).thenReturn(findsUrls);

        List<URL> result = crudUrlService.getAllURLsByUserId(userId);

        assertEquals(findsUrls, result);

        verify(urlRepository, times(1)).findAllByUserId(userId);
    }
}
