package ua.goit.shortener.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.CrudUrlService;
import ua.goit.shortener.url.services.URLService;
import ua.goit.shortener.url.services.impl.URLServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
    private final CrudUrlService crudUrlService;
    private final URLServiceImpl urlServiceImpl;

    @Autowired
    public UrlController(CrudUrlService crudUrlService, URLServiceImpl urlServiceImpl) {
        this.crudUrlService = crudUrlService;
        this.urlServiceImpl = urlServiceImpl;
    }

    @GetMapping("/active")
    public ResponseEntity<List<UrlDTO>> getActiveURLs() {
        List<URL> activeUrls = crudUrlService.getAllURLs()
                .stream()
                .filter(url -> url.getClickCount() > 0).toList();

        List<UrlDTO> urlDTOs = activeUrls.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(urlDTOs);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UrlDTO>> getAllURLs() {
        List<URL> urls = crudUrlService.getAllURLs();
        List<UrlDTO> urlDTOs = urls.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(urlDTOs);
    }

    @GetMapping("/info/{shortURL}")
    public ResponseEntity<UrlDTO> getURLInfo(@PathVariable String shortURL) {
        UrlDTO urlInfo = urlServiceImpl.getURLInfo(shortURL);
        if (urlInfo != null) {
            return ResponseEntity.ok(urlInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createShortURL(@RequestBody String originalURL) {
        if (urlServiceImpl.isValidURL(originalURL)) {
            String shortURL = urlServiceImpl.createShortURL(originalURL);
            return ResponseEntity.ok(shortURL);
        } else {
            return ResponseEntity.badRequest().body("Недійсний URL");
        }
    }

    @DeleteMapping("/delete/{shortURL}")
    public ResponseEntity<Void> deleteURL(@PathVariable String shortURL) {
        Optional<URL> existingURL = crudUrlService.getURLByShortURL(shortURL);
        if (existingURL.isPresent()) {
            crudUrlService.deleteURL(shortURL);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //треба перенести в сервіс і прибрати з контроллера.
    @GetMapping("/{shortURL}")
    public ResponseEntity<String> checkShortURLExpiry(@PathVariable String shortURL) {
        Optional<String> longURL = urlServiceImpl.getShortURLWithCheckExpiry(shortURL);

        if (longURL.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(longURL.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Коротка URL-адреса не знайдена або термін дії минув");
        }
    }

    private UrlDTO mapToDTO(URL url) {
        UrlDTO dto = new UrlDTO();
        dto.setShortURL(url.getShortURL());
        dto.setOriginalURL(url.getLongURL());
        dto.setCreateDate(url.getCreateDate());
        dto.setExpiryDate(url.getExpiryDate());
        dto.setClickCount(url.getClickCount());
        return dto;
    }
}
