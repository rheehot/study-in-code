package code.tech.spring.study.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

public class DistinctTest {
    @Test
    void distinctRemovesDuplicatedItems() {
        // When
        List<Integer> integers = Stream.of(1, 2, 3, 4, 5, 5, 4, 3, 2, 1)
                                       .distinct()
                                       .toList();

        // Expected
        Assertions.assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, integers.toArray());
    }
}
