package ua.goit.shortener.user.dto;

import lombok.Data;
import ua.goit.shortener.url.entity.URL;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String nickName;
    private String password;
    private String email;
    private List<URL> urls = new ArrayList<>();

}
