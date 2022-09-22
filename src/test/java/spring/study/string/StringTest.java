package spring.study.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class StringTest {

    @Nested
    @DisplayName("Replace")
    class Replace {
        @Test
        @DisplayName("""
        replaceAll("[AD]", "ABCD") = "BC"
        """)
        void replaceAll() {
            String sample = "ABCD";
            Assertions.assertEquals("BC", sample.replaceAll("[AD]", ""));
        }
    }
}
