package ua.goit.shortener.springProfiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("production")
class ProfileProdTests {

    @Autowired
    Environment environment;

    @Test
    void profileIsProd() {
        String expected = environment.getActiveProfiles()[0];
        String[] activeProfiles = environment.getActiveProfiles();
        System.out.println("");
        System.out.println("");
        System.out.println("_________________");
        System.out.println("");
        System.out.println(Arrays.toString(activeProfiles));
        System.out.println("");
        System.out.println("");
        System.out.println("_________________");
        System.out.println("");
        String actual = "production";

        Assertions.assertEquals(expected, actual);
    }
}
