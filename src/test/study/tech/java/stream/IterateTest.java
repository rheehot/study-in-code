package tech.java.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IterateTest {

    @Test
    void generate_infinite_stream() {
        assertThrows(OutOfMemoryError.class, () -> LongStream.iterate(0, i -> i + 1).toArray());
    }

    @Test
    void generate_limited_stream() {
        int n = 10_000_000;
        long result = LongStream.iterate(1L, i -> i + 1)
                                .limit(n)
                                .reduce(0, Long::sum);
        assertEquals(50_000_005_000_000L, result);
    }
}
