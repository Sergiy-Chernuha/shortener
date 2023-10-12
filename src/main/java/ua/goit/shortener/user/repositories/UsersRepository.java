package ua.goit.shortener.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.goit.shortener.user.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
}