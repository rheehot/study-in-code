package spring.study.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ParseTest {
    @SuppressWarnings("ResultOfMethodCallIgnored")
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
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    @DisplayName("""
    byte 타입 변수가 표현할 수있는 값 범위는 -128 to 127 입니다.
    따라서 255(0xFF)는 parse 할 수 있는 값 범위를 초과합니다.""")
    void parseOutOfRangeError() {
        Executable testCode = () -> Byte.parseByte("FF", 16);
        Assertions.assertThrows(NumberFormatException.class, testCode);
    }
}
