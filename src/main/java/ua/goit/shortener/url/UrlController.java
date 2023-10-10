package ua.goit.shortener.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.CrudUrlService;
import ua.goit.shortener.url.services.URLService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
    private final CrudUrlService crudUrlService;
    private final URLService urlService;

    @Autowired
    public UrlController(CrudUrlService crudUrlService, URLService urlService) {
        this.crudUrlService = crudUrlService;
        this.urlService = urlService;
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
        UrlDTO urlInfo = urlService.getURLInfo(shortURL);
        if (urlInfo != null) {
            return ResponseEntity.ok(urlInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createShortURL(@RequestBody String originalURL) {
        if (urlService.isValidURL(originalURL)) {
            String shortURL = urlService.createShortURL(originalURL);
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

    private UrlDTO mapToDTO(URL url) {
        UrlDTO dto = new UrlDTO();
        dto.setShortURL(url.getShortURL());
        dto.setOriginalURL(url.getLongURL());
        dto.setCreateDate(url.getCreateDate());
        dto.setClickCount(url.getClickCount());
        return dto;
    }
}
