package ua.goit.shortener.url.entity;

import jakarta.persistence.*;
import lombok.Data;
import ua.goit.shortener.user.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private Date createDate;

    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;

    @Column(name = "CLICKS_COUNT")
    private Integer clickCount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public URL(String shortURL) {
    }

    public URL() {
    }

    public void setExpiryShortURL() {
        // Встановлюємо термін придатності на 2 доби від поточної дати створення
        LocalDateTime newExpiryDate = LocalDateTime.ofInstant(createDate.toInstant(), ZoneId.systemDefault()).plusDays(2);
        this.expiryDate = Date.from(newExpiryDate.atZone(ZoneId.systemDefault()).toInstant());
    }
}
