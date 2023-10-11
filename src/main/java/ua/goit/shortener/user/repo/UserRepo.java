package ua.goit.shortener.user.repo;

import org.springframework.data.repository.CrudRepository;
import ua.goit.shortener.user.entity.User;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);
    //Optional<User> findByGoogleId(String aLong);
}
