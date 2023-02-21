package code.tech.spring.study.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("StringUtils")
public class StringUtilsTest {
    @Test
    void hello() {
        System.out.println("hello");
    }

    @Nested
    class Remove {
        @Test
        @DisplayName("""
                remove("ABCD", "CD") = "AB"
                """)
        void givenABCD_whenRemoveCD_thenEqualAB() {
            String sample = "ABCD";
            Assertions.assertEquals("AB", StringUtils.remove(sample, "CD"));
        }

        @Test
        @DisplayName("""
                remove("ABCD", "AD") = "ABCD"
                """)
        void givenABCD_whenRemoveAD_thenEqualABCD() {
            String sample = "ABCD";
            Assertions.assertEquals("ABCD", StringUtils.remove(sample, "AD"));
        }
    }

    @Nested
    class SubString {
        @Test
        @DisplayName("""
                subString("ABD", "ABD".length()) = ""
                """)
        void whenStartAtOverIndex_thenReturnEmptyString() {
            String sample = "ABC";
            Assertions.assertEquals("", StringUtils.substring(sample, sample.length()));
        }
    }
}
