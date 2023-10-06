package ua.goit.shortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.goit.shortener.entities.ShortenedUrl;
import ua.goit.shortener.services.ShortenedUrlService;

@RestController
@RequestMapping("/api/shortened-url")
public class ShortenedUrlController {
    private final ShortenedUrlService shortenedUrlService;
    @Autowired
    public ShortenedUrlController(ShortenedUrlService shortenedUrlService) {
        this.shortenedUrlService = shortenedUrlService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createShortenedUrl(@RequestParam String originalUrl, @RequestParam String user) {
        try {
            ShortenedUrl shortUrl = shortenedUrlService.createShortenedUrl(originalUrl, user);
            return ResponseEntity.ok("Shortened URL created: " + shortUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create shortened URL: " + e.getMessage());
        }
    }
}
