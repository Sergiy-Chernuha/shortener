package ua.goit.shortener.url.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.goit.shortener.url.services.impl.URLServiceImpl;

@Controller
public class RedirectController {
    private final URLServiceImpl urlService;

    public RedirectController(URLServiceImpl urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortUrl}")
    public String redirectToOriginalURL(@PathVariable String shortUrl, HttpServletResponse response) {
        if (urlService.isValidShortURL(shortUrl)) {
            String originalURL = urlService.getOriginalURL(shortUrl);

            if (originalURL != null) {
                urlService.incrementClickCount(shortUrl); //мені здається його саме сюди вставити
                return "redirect:" + originalURL;
            }
        }

        return "redirect:/"; // Редірект на головну сторінку або обробка помилки
        }
    }
