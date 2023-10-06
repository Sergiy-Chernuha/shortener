package ua.goit.shortener.url.entity;

import jakarta.persistence.*;
import lombok.Data;
import ua.goit.shortener.user.User;

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

    @Column(name = "CLICKS_COUNT")
    private Integer clicks;

    public URL(String shortURL) {
    }

    public URL() {

    }
}
