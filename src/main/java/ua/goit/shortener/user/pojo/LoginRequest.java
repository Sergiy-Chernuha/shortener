package ua.goit.shortener.user.pojo;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
