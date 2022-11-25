package spring.study.number;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
