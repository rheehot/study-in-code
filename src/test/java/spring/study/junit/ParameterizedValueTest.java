package spring.study.junit;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ParameterizedValueTest {
    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void methodSource(float[] expected, float[] actual) {
        System.out.println(Arrays.toString(expected));
        System.out.println(Arrays.toString(actual));
    }

    static Stream<Arguments> testCaseSupplier() {
        return Stream.of(
                arguments(new float[]{1, 2, 3}, new float[]{4, 5, 6}),
                arguments(new float[]{1, 2, 3}, new float[]{4, 5, 6})
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 3.0})
    void valueSource(double value) {
        System.out.println(value);
    }
}
