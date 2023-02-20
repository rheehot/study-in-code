package spring.study.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExponentialNotationTest {
    @Test
    void exponentialNotation() {
        double expNumber = 5e+8;
        long expInt = (long) 5e+12;
        double number = 500000000.0;
        log.info("expNumber: {}", expNumber);
        log.info("number: {}", number);
        Assertions.assertEquals(500000000, number);
        Assertions.assertEquals(5000000000000L, expInt);
        Assertions.assertEquals(500000000, expNumber);
    }

    @Test
    void convertToExponential() {
        double number = 595000000.0;
        String expNumber = String.format("%.2e", number);
        log.info("expNumber: {}", expNumber);
        Assertions.assertEquals("5.95e+08", expNumber);
    }
}
