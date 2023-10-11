package ua.goit.shortener.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    @GetMapping("/userInfo")
    @PreAuthorize("hasRole({'user','admin'})")
    public @ResponseBody Map<String,String> getUserInfo(@AuthenticationPrincipal UserDetails userDetails){
        return Map.of("userName", userDetails.getUsername());
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
