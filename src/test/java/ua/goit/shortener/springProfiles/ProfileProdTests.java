package ua.goit.shortener.springProfiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.Objects;

//@SpringBootTest
//@ActiveProfiles("production")
//class ProfileProdTests {
//
//    @Autowired
//    Environment environment;
//
//    @Test
//    void profileIsProd() {
//        String expected = environment.getProperty("test_prop");
//        String actual = "PROD";
//
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void profileIsNotProd() {
//        String actual = environment.getProperty("test_prop");
//
//        Assert.isTrue(Objects.equals(actual, "PROD"), "INCORRECT ENVIRONMENT CONFIG");
//    }
//}
