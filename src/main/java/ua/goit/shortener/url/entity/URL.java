package ua.goit.shortener.url.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import ua.goit.shortener.user.entity.User;

import java.util.Date;

@Entity
@Table(name = "URL")
@Data
public class URL {
    @Id
    @Column(name = "SHORT_URL")
    private String shortURL;

    @Getter
    @Column(name = "LONG_URL")
    private String longURL;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "CLICKS_COUNT")
    private Integer clickCount;

    public URL(String shortURL) {
        this.shortURL = shortURL;
    }

    public URL() {
    }

    // Геттер для shortURL
    public String getShortURL() {
        return shortURL;
    }

    // Сеттер для shortURL
    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    // Геттер для longURL
    public String getLongURL() {
        return longURL;
    }

    // Сеттер для longURL
    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    // Геттер для createDate
    public Date getCreateDate() {
        return createDate;
    }

    // Сеттер для createDate
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    // Геттер для clickCount
    public Integer getClickCount() {
        return clickCount;
    }

    // Сеттер для clickCount
    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public URL() {

    }
}
