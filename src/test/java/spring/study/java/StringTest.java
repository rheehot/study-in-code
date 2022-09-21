package spring.study.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    void floatingFormat() {
        String result = String.format("%.1f", 123.456);
        Assertions.assertEquals("123.5", result);

        result = String.format("%05.1f", 12.345);
        Assertions.assertEquals("012.3", result);

        result = String.format("%06.1f", -123.432);
        Assertions.assertEquals("-123.4", result);
    }
}
