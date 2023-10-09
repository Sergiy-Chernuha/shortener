package ua.goit.shortener.springProfiles;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.Objects;

@SpringBootTest
@ActiveProfiles("dev")
class ProfileDevTests {

//    @BeforeEach
    @Autowired
    Environment environment;

    @Test
    void profileIsDev() {


        //WHEN

        String expected = environment.getProperty("test_prop");
        String actual = "DEV";

        //THEN
        Assertions.assertEquals(expected, actual);
        System.out.println();
        System.out.println("Profile is DEV");

    }

    @Test
    void profileIsNotDev() {

        Assert.isTrue(Objects.equals(environment.getProperty("test_prop"), "DEV"), "INCORRECT ENVIRONMENT CONFIG");

    }

}
