package spring.study.string;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
