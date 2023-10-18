package ua.goit.shortener.url.controller;

import jakarta.validation.constraints.NotNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.CrudUrlService;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.url.services.impl.URLServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CrudUrlService crudUrlService;

    @MockBean
    URLService urlServiceImpl;

    @Test
    void getAllURLs() throws Exception {
        URL ulr1 = new URL();
        ulr1.setShortURL("short_url1");

        URL url2 = new URL();
        url2.setShortURL("short_url2");

        List urls = Arrays.asList(ulr1, url2);

        when(crudUrlService.getAllURLs()).thenReturn(urls);
        when(urlServiceImpl.mapToDTO(any(URL.class))).then(value -> {
            UrlDTO dto = new UrlDTO();
            URL url = value.getArgument(0, URL.class);
            dto.setShortURL(url.getShortURL());
            return dto;
        });

        this.mockMvc
                .perform(get("/api/v1/urls/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shortURL").value("short_url1"))
                .andExpect(jsonPath("$[1].shortURL").value("short_url2"))
        ;
    }

    @Test
    void getActiveURLs() throws Exception {
        URL ulr1 = new URL();
        ulr1.setShortURL("isActive");

        URL url2 = new URL();
        url2.setShortURL("isNotActive");

        List urls = Arrays.asList(ulr1, url2);

        when(crudUrlService.getAllURLs()).thenReturn(urls);
        when(urlServiceImpl.isActiveShortURL(any(URL.class))).then(invocation -> {
            URL url = invocation.getArgument(0, URL.class);
            return url.getShortURL().equals("isActive");
        });
        when(urlServiceImpl.mapToDTO(any(URL.class))).then(invocation -> {
            UrlDTO dto = new UrlDTO();
            URL url = invocation.getArgument(0, URL.class);
            dto.setShortURL(url.getShortURL());
            return dto;
        });

        this.mockMvc
                .perform(get("/api/v1/urls/active"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    void getURLInfo() {
    }

    @Test
    void createShortURL() {
    }

    @Test
    void deleteURL() {
    }

    @Test
    void updateURL() {
    }
}