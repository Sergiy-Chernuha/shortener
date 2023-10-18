package ua.goit.shortener.reg;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.services.impl.UserServicesImpl;

import java.util.Optional;

@RestController
@RequestMapping({"/api/v1/urls", "/api/v2/urls"})
public class RegistrationController {
    private final UserServicesImpl userServiceImpl;

    @Autowired
    public RegistrationController(UserServicesImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(@RequestBody @Valid User user) {
        try {
            Optional<User> existingUser = userServiceImpl.findByEmail(user.getEmail());

            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body("User with this email already exists");
            }
            User regUser = userServiceImpl.saveUser(user);

            return ResponseEntity.ok("User registered successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }
    }
}
