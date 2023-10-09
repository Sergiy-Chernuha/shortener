package ua.goit.shortener.springProfiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.Objects;

@SpringBootTest
@ActiveProfiles("production")
class ProfileProdTests {

    @Autowired
    Environment environment;

    @Test
    void profileIsProd() {


        //WHEN

        String expected = environment.getProperty("test_prop");
        String actual = "PROD";

        //THEN
        Assertions.assertEquals(expected, actual);
        System.out.println();
        System.out.println("Profile is PROD");

    }

    @Test
    void profileIsNotProd() {
        Assert.isTrue(Objects.equals(environment.getProperty("test_prop"), "PROD"), "INCORRECT ENVIRONMENT CONFIG");
    }

}
