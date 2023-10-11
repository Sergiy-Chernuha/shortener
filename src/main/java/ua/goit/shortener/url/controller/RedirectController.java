package ua.goit.shortener.url.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.shortener.url.services.impl.URLServiceImpl;


@Controller
public class RedirectController {
    private final URLServiceImpl urlService;

    public RedirectController(URLServiceImpl urlService) {
        this.urlService = urlService;
    }

    @RequestMapping("/{shortUrl}")
    public ModelAndView redirectFromShortUrl(@PathVariable String shortUrl) {
        String originalURL = urlService.getOriginalURL(shortUrl);

        if (originalURL != null) {
            return new ModelAndView("redirect:" + originalURL);
        } else {
            return new ModelAndView("not_found"); // Потрібно створити сторінку not_found.html
        }
    }
}