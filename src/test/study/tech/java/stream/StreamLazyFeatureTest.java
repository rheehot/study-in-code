package tech.java.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class StreamLazyFeatureTest {
    @Test
    void shortCircuit() {
        String find = IntStream.of(10, 5, 2, 20, 1)
                .filter(num -> num < 3)
                .mapToObj(String::valueOf)
                .findFirst()
                .orElse("");
        Assertions.assertEquals("2", find);
    }

    @Test
    void loggableShortCircuit() {
        final int target = 3;
        String find = IntStream.of(10, 5, 2, 20, 1)
                .filter(num -> {
                    System.out.println("filtering: " + num);
                    return num < target;
                })
                .mapToObj(num -> {
                    System.out.println("mapping: " + num);
                    return String.valueOf(num);
                })
                .findFirst()
                        .orElse("");
        Assertions.assertEquals( "2", find);

        /* Output:
        filtering: 10
        filtering: 5
        filtering: 2
        mapping: 2
         */
    }
}
