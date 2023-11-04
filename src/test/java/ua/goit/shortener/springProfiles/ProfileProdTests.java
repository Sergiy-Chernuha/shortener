package ua.goit.shortener.springProfiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("production")
class ProfileProdTests {

    @Autowired
    Environment environment;

    @Test
    void profileIsProd() {
        String expected = environment.getProperty("test_prop");
        String actual = "PROD";

        Assertions.assertEquals(expected, actual);
    }
}
