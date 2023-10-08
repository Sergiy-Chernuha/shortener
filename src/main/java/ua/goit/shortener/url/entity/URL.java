package ua.goit.shortener.url.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "URL")
@Data
public class URL {
    @Id
    @Column(name = "SHORT_URL")
    private String shortURL;

    @Column(name = "LONG_URL")
    private String longURL;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CLICKS_COUNT")
    private Integer clicks;

    @Column(name = "USER_ID")
    private Long user_id;

    public URL(String shortURL) {
    }

    public URL() {

    }
}
