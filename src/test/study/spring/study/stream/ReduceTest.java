package spring.study.stream;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReduceTest {
    @Test
    void reduceCharsToOneString() {
        Stream<Character> chars = Stream.of('A', 'B', 'C');

        String result = chars.map(String::valueOf)
                .reduce("", (partialString, element) -> partialString + element);

        Assertions.assertThat(result).isEqualTo("ABC");
    }
}
