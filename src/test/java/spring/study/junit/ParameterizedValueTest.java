package spring.study.junit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParameterizedValueTest {
    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void test(float[] expected, float[] actual) {
        System.out.println(Arrays.toString(expected));
        System.out.println(Arrays.toString(actual));
    }

    static Stream<Arguments> testCaseSupplier() {
        return Stream.of(
                arguments(new float[]{1, 2, 3}, new float[]{4, 5, 6}),
                arguments(new float[]{1, 2, 3}, new float[]{4, 5, 6})
        );
    }
}
