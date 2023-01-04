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

    @Nested
    @DisplayName("Split")
    class Split {
        @Test
        @DisplayName("""
                "123,456,789".split(",") returns "123", "456", "789"\040""")
        void split(){
            Assertions.assertArrayEquals(new String[]{"123", "456", "789"}, "123,456,789".split(","));
        }
    }
}
