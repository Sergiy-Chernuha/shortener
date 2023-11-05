package ua.goit.shortener.springProfiles;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
class ProfileDevTests {

    @Autowired
    Environment environment;

    @Test
    void profileIsDev() {
        String expected = environment.getActiveProfiles()[0];
        String actual = "dev";

        assertEquals(expected, actual);
    }
}
