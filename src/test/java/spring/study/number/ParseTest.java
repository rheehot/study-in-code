package spring.study.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParseTest {
    @Test
    @DisplayName("Integer.parseInt()는 white space 를 허용하지 않는다")
    void integerIncludingLeadingWhiteSpaceNumber() {
        String sample = "123 ";
        Assertions.assertThrows(NumberFormatException.class, () -> Integer.parseInt(sample));
    }

    @Test
    @DisplayName("Double.parseDouble()는 white space 를 허용한다")
    void doubleIncludingLeadingWhiteSpaceNumber() {
        String sample = "123.123 ";
        Assertions.assertEquals(123.123, Double.parseDouble(sample));
    }

    @Test
    void parseByte() {
        Assertions.assertEquals(0x7F, Byte.parseByte("7F", 16));
        Assertions.assertThrows(NumberFormatException.class, () -> Byte.parseByte("FF", 16)); // Out of range (max : 0x7F)
        Assertions.assertEquals(0xFF, Integer.parseInt("FF", 16));
    }
}
