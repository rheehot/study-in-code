package code.tech.spring.study.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class PatternTest {

    @Test
    void matchesRegEx() {
        String pattern = ".*\\d{4}.*";
        String target = "1234";

        Assertions.assertTrue(Pattern.matches(pattern, target));
    }

    @Test
    void matchesOneOfList() {
        String pattern = "abc|def|ghi";
        String target = "abc";

        Assertions.assertTrue(Pattern.matches(pattern, target));
    }
}
