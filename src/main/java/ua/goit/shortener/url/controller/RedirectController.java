package ua.goit.shortener.url.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.impl.URLServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;


@Controller
public class RedirectController {
    private final URLServiceImpl urlService;

    public RedirectController(URLServiceImpl urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/shorter/t3/{shortURL}")
    public void redirectToOriginalURL(@PathVariable String shortURL, HttpServletResponse response) throws IOException {
        String originalURL = urlService.getOriginalURL("shorter/t3/" + shortURL);

        response.sendRedirect(originalURL);
//
//        if (originalURL != null) {
//            return new ModelAndView("redirect:" + originalURL);
//        } else {
//            return new ModelAndView("not_found"); // Потрібно створити сторінку not_found.html
//        }
    }
}