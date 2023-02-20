package code.spring.study.string;

import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
