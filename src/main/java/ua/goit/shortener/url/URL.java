package ua.goit.shortener.url;

import jakarta.persistence.*;
import lombok.Data;
import ua.goit.shortener.user.User;

import java.util.Date;

@Entity
@Table(name = "URL")
@Data
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LONG_URL")
    private String longURL;

    @Column(name = "SHORT_URL")
    private String shortURL;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "user_id") // Назва колонки для зв'язку
    private User user;


    public URL(String shortURL) {
    }
}
