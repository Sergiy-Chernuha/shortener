package ua.goit.shortener.url.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class InputURLDTO {
    private String originalURL;
    @Getter
    private Long userId;
}
