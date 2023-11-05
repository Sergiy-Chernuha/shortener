package ua.goit.shortener.url.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.goit.shortener.url.dto.InputURLDTO;
import ua.goit.shortener.url.dto.UrlDTO;
import ua.goit.shortener.url.entity.URL;
import ua.goit.shortener.url.services.CrudUrlService;
import ua.goit.shortener.url.services.URLService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/v1/urls", "/api/v2/urls"})
public class UrlController {
    private final CrudUrlService crudUrlService;
    private final URLService urlServiceImpl;

    @Autowired
    public UrlController(CrudUrlService crudUrlService, @Qualifier("V1") URLService urlServiceImpl) {
        this.crudUrlService = crudUrlService;
        this.urlServiceImpl = urlServiceImpl;
    }

    @GetMapping("/active")
    @Operation(
            summary = "All active URL",
            description = "Get all active URL.",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Getting a list of all active URL on the site"),
                    @ApiResponse(responseCode = "404", description = "No active URL")
            }
    )
    public ResponseEntity<List<UrlDTO>> getActiveURLs() {
        List<URL> activeUrls = crudUrlService.getAllURLs();
        List<UrlDTO> urlDTOs = activeUrls.stream().filter(urlServiceImpl::isActiveShortURL).map(urlServiceImpl::mapToDTO).collect(Collectors.toList());

        return ResponseEntity.ok(urlDTOs);
    }

    @GetMapping("/active/{userId}")
    @Operation(
            summary = "Active URLs by User ID",
            description = "Get active URLs for a specific user by user ID.",
            responses = {
                    @ApiResponse(responseCode = "302", description = "List of active URLs for the specified user"),
                    @ApiResponse(responseCode = "404", description = "No active URLs found for the user")
            }
    )
    public ResponseEntity<List<UrlDTO>> getActiveURLsByUserId(@PathVariable Long userId) {
        List<URL> activeUrls = crudUrlService.getAllURLsByUserId(userId);
        List<UrlDTO> urlDTOs = activeUrls.stream().filter(urlServiceImpl::isActiveShortURL).map(urlServiceImpl::mapToDTO).collect(Collectors.toList());

        return ResponseEntity.ok(urlDTOs);
    }

    @GetMapping("/all")
    @Operation(
            summary = "All URLs",
            description = "Get a list of all URLs.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all URLs"),
                    @ApiResponse(responseCode = "404", description = "No URLs found")
            }
    )
    public ResponseEntity<List<UrlDTO>> getAllURLs(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        List<URL> urls = crudUrlService.getAllURLs();
        List<UrlDTO> urlDTOs = urls.stream().map(urlServiceImpl::mapToDTO).collect(Collectors.toList());

        if (requestURI.contains("/api/v2")) {
            // Логіка для версії 2: сортування URL за назвою в зворотньому алфавітному порядку
            urlDTOs.sort((url1, url2) -> url2.getShortURL().compareTo(url1.getShortURL()));
        }

        return ResponseEntity.ok(urlDTOs);
    }

    @GetMapping("/all/{userId}")
    @Operation(
            summary = "All URLs by User ID",
            description = "Get all URLs for a specific user by user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all URLs for the specified user"),
                    @ApiResponse(responseCode = "404", description = "No URLs found for the user")
            }
    )
    public ResponseEntity<List<UrlDTO>> getAllURLs(@PathVariable Long userId) {
        List<URL> urls = crudUrlService.getAllURLsByUserId(userId);
        List<UrlDTO> urlDTOs = urls.stream().map(urlServiceImpl::mapToDTO).collect(Collectors.toList());

        return ResponseEntity.ok(urlDTOs);
    }

    @GetMapping("/info/{shortURL}")
    @Operation(
            summary = "URL Info",
            description = "Get information about a specific URL by its short URL.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Information about the URL"),
                    @ApiResponse(responseCode = "404", description = "URL not found")
            }
    )
    public ResponseEntity<UrlDTO> getURLInfo(@PathVariable String shortURL) {
        UrlDTO urlInfo = urlServiceImpl.getURLInfo("shorter/t3/" + shortURL);

        if (urlInfo != null) {
            return ResponseEntity.ok(urlInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create Short URL",
            description = "Create a short URL for a given original URL.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Short URL created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid URL provided")
            }
    )
    public ResponseEntity<String> createShortURL(@RequestBody InputURLDTO inputURL) {
        if (urlServiceImpl.isValidURL(inputURL.getOriginalURL())) {
            Long userId = inputURL.getUserId();
            String shortURL = urlServiceImpl.saveShortURL(userId, inputURL.getOriginalURL());

            return ResponseEntity.ok(shortURL);
        } else {
            return ResponseEntity.badRequest().body("Недійсний URL");
        }
    }

    @DeleteMapping("/delete/{shortURL}")
    @Operation(
            summary = "Delete URL",
            description = "Delete a URL by its short URL.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "URL deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "URL not found")
            }
    )
    public ResponseEntity<Void> deleteURL(@PathVariable String shortURL) {
        String inputShortURL = "shorter/t3/" + shortURL;
        Optional<URL> existingURL = crudUrlService.getURLByShortURL(inputShortURL);

        if (existingURL.isPresent()) {
            crudUrlService.deleteURL(inputShortURL);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{shortURL}")
    @Operation(
            summary = "Update URL",
            description = "Update the original URL for a short URL.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "URL updated successfully"),
                    @ApiResponse(responseCode = "404", description = "URL not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid URL provided")
            }
    )
    public ResponseEntity<String> updateURL(@PathVariable String shortURL, @RequestBody InputURLDTO inputURLDTO) {
        String newOriginalURL = inputURLDTO.getOriginalURL();

        if (urlServiceImpl.isValidURL(newOriginalURL)) {
            boolean updated = urlServiceImpl.updateShortURL("shorter/t3/" + shortURL, newOriginalURL);

            if (updated) {
                return ResponseEntity.ok("URL updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
