package ua.goit.shortener.user.repositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.goit.shortener.user.entity.ERole;
import ua.goit.shortener.user.entity.Role;

import java.util.Optional;
@Hidden
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
