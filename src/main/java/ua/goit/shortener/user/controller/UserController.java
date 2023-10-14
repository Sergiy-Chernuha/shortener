package ua.goit.shortener.user.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repositories.UsersRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UsersRepository userRepo;

    public UserController(UsersRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/add")
    public @ResponseBody User createUser(@RequestBody User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @GetMapping
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }
}
