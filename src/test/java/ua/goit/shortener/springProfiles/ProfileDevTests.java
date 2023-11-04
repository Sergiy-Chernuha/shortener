package ua.goit.shortener.springProfiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class ProfileDevTests {

    @Autowired
    Environment environment;

    @Test
    void profileIsDev() {
        String expected = environment.getProperty("test_prop");
        String actual = "DEV";

        Assertions.assertEquals(expected, actual);
    }
}
