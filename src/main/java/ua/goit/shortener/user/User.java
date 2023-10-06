package ua.goit.shortener.user;

import jakarta.persistence.*;
import lombok.Data;
import ua.goit.shortener.url.entity.URL;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "PASSWORLD")
    private String passworld;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<URL> urls = new ArrayList<>();


}
