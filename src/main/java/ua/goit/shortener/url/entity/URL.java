package ua.goit.shortener.url.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import ua.goit.shortener.user.entity.User;

import java.util.Date;

@Entity
@Table(name = "URL")
@Data
public class URL {
    // Геттер для shortURL
    @Getter
    @Id
    @Column(name = "SHORT_URL")
    private String shortURL;

    // Геттер для longURL
    @Getter
    @Column(name = "LONG_URL")
    private String longURL;

    // Геттер для createDate
    @Getter
    @Column(name = "CREATE_DATE")
    private Date createDate;

    // Геттер для clickCount
    @Getter
    @Column(name = "CLICKS_COUNT")
    private Integer clickCount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @jakarta.persistence.Id
    private Long id;


    public URL(String shortURL) {
        this.shortURL = shortURL;
    }

    public URL() {
    }

    // Сеттер для shortURL
    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    // Сеттер для longURL
    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    // Сеттер для createDate
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    // Сеттер для clickCount
    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

}
