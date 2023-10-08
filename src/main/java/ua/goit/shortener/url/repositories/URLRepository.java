package ua.goit.shortener.url.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.shortener.url.entity.URL;

public interface URLRepository extends JpaRepository<URL, String> {
}
