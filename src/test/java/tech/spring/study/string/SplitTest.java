package tech.spring.study.string;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class SplitTest {
    @Test
    void splitIntoCharArray() {
        String sample = "ABC";
        char[] chars = sample.toCharArray();
        Assertions.assertThat(new char[] { 'A', 'B', 'C' }).isEqualTo(chars);
    }

    @Test
    void splitIntoStringCharArray() {
        Pattern COMPILE = Pattern.compile("");
        String sample = "ABC";
        String[] chars = COMPILE.split(sample);
        Assertions.assertThat(new String[] { "A", "B", "C" }).isEqualTo(chars);
    }
}
