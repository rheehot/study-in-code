package tech.java.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParallelTest {
    @Test
    void parallelSum() {
        int n = 10_000_000;
        Long result = LongStream.rangeClosed(1, n)
                                .parallel()
                                .reduce(0L, Long::sum);
        assertEquals(50_000_005_000_000L, result);
    }
}
