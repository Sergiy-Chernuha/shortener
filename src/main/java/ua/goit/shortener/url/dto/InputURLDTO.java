package ua.goit.shortener.url.dto;

import lombok.Data;

@Data
public class InputURLDTO {
    private String originalURL;

    public InputURLDTO() {
    }

    public InputURLDTO(String originalURL) {
        this.originalURL = originalURL;
    }
}
