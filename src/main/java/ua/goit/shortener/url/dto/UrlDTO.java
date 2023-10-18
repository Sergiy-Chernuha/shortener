package ua.goit.shortener.url.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UrlDTO {
    private String shortURL;
    private String originalURL;
    private Date createDate;
    private Date expiryDate;
    private Integer clickCount;
}
