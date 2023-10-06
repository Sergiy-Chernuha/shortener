package ua.goit.shortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.shortener.entities.ShortenedUrl;

public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, Long> {
}
