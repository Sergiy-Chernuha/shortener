package ua.goit.shortener.url.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.goit.shortener.url.entity.URL;

@Repository
public interface URLRepository extends JpaRepository<URL, String> {
}
