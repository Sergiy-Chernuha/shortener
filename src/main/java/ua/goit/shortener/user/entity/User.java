package ua.goit.shortener.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import ua.goit.shortener.url.entity.URL;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String nickName;


    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<URL> urls = new ArrayList<>();

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;
}
