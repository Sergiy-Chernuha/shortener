package ua.goit.shortener.url.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.goit.shortener.url.services.CacheUrlService;

import java.io.IOException;

@RequestMapping({"/api/v1/urls", "/api/v2/urls"})
@Controller
public class RedirectController {
    private final CacheUrlService cacheUrlService;

    @Autowired
    public RedirectController(CacheUrlService cacheUrlService) {
        this.cacheUrlService = cacheUrlService;
    }

    @GetMapping("/shorter/t3/{shortURL}")
    @Operation(
            summary = "Redirect to Original URL",
            description = "Redirects to the original URL associated with the provided short URL.",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Redirect to the original URL"),
                    @ApiResponse(responseCode = "404", description = "Short URL not found")
            }
    )
    public void redirectToOriginalURL(@PathVariable String shortURL, HttpServletResponse response) throws IOException {
        String inputShortURL = "shorter/t3/" + shortURL;
        String originalURL = cacheUrlService.getOriginalURL(inputShortURL);

        if (originalURL != null) {
            response.sendRedirect(originalURL);
        } else {
            response.sendError(404, "Short URL not found");
        }
    }
}
