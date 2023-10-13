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
        String originalURL = urlService.getOriginalURL("shorter/t3/" + shortURL);
        if (originalURL != null) {
            urlService.incrementClickCount("shorter/t3/" + shortURL); //мені здається його саме сюди вставити
            response.sendRedirect(originalURL);
        }
    }
}