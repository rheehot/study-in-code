package code.spring.study.string;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class StringTest {

    @Test
    void sortStringArray() {
        String[] array = { "A", "C", "B" };
        List<String> strings = Arrays.stream(array).sorted().toList();
        Assertions.assertArrayEquals(new String[]{"A", "B", "C"}, strings.toArray());
    }

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

        @SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
        @Test
        void splitEachChar() {
            Assertions.assertArrayEquals(new String[]{"A","B"}, "AB".split(""));
        }

        @Test
        @DisplayName("""
                "123,456,789".split(",") returns "123", "456", "789"\040""")
        void split(){
            Assertions.assertArrayEquals(new String[]{"123", "456", "789"}, "123,456,789".split(","));
        }
    }
}
