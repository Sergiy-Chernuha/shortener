package ua.goit.shortener.url.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.goit.shortener.url.services.impl.URLServiceImpl;


@Controller
public class RedirectController {
    private final URLServiceImpl urlSvc;

    public RedirectController(URLServiceImpl urlSvc) {
        this.urlSvc = urlSvc;
    }

    @GetMapping("/{shortUrl}")
    public String redirectToOriginalURL(@PathVariable String shortUrl) {
        if (urlSvc.isValidShortURL(shortUrl)) {
            String originalURL = urlSvc.getOriginalURL(shortUrl);
            return "redirect:" + originalURL;
        } else {
            return "redirect:/"; // Редірект на головну сторінку або обробка помилки
        }
    }

}
