package ua.goit.shortener.url.entity;

import jakarta.persistence.*;
import lombok.Data;
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
    private Integer clicks;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public URL() {
    }
}