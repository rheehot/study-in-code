package tech.spring.study.stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class ReduceTest {
    @Test
    void reduceCharsToOneString() {
        Stream<Character> chars = Stream.of('A', 'B', 'C');

        String result = chars.map(String::valueOf)
                .reduce("", (partialString, element) -> partialString + element);

        Assertions.assertThat(result).isEqualTo("ABC");
    }
}
