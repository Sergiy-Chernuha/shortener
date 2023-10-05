package ua.goit.shortener.url;

import lombok.Data;

import java.util.Date;

@Data
public class UrlDTO {
    private String shortURL;
    private String originalURL;
    private Date createDate;
    private int clickCount;
}
