package ua.goit.shortener.url.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.goit.shortener.url.services.impl.URLServiceImpl;

import java.io.IOException;

@Controller
public class RedirectController {
    private final URLServiceImpl urlService;

    public RedirectController(URLServiceImpl urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/shorter/t3/{shortURL}")
    public void redirectToOriginalURL(@PathVariable String shortURL, HttpServletResponse response) throws IOException {
        String inputShortURL = "shorter/t3/" + shortURL;
        String originalURL = urlService.getOriginalURL(inputShortURL);

        if (originalURL != null) {
            urlService.incrementClickCount(inputShortURL);
            response.sendRedirect(originalURL);
        }
    }
}
