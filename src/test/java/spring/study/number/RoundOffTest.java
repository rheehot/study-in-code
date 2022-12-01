package spring.study.number;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class RoundOffTest {
    @Test
    void roundOff() {
        double sample = 123.123;
        BigDecimal bigDecimal = new BigDecimal(sample);
        BigDecimal roundedOff = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        Assertions.assertEquals(123.120, roundedOff.doubleValue());
    }
}
