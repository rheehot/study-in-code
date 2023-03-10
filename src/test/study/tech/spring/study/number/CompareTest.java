package tech.spring.study.number;

import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 참고 사이트 : <a href="https://www.baeldung.com/java-comparing-doubles">Comparing Doubles in Java (Baeldung)</a>
 */
public class CompareTest {
    double d1;
    double d2;

    @BeforeEach
    void setUp() {
        d1 = 0;
        for (int i = 1; i <= 8; i++) {
            d1 += 0.1;
        }
        d2 = 0.1 * 8;
    }

    @Test
    void useEqualOperator() {
        double d1 = 0;
        for (int i = 1; i <= 8; i++) {
            d1 += 0.1;
        }

        double d2 = 0.1 * 8;
        System.out.println(d1);
        System.out.println(d2);
    }

    @Test
    void usePlainJavaWay() {
        double epsilon = 0.000001d;
        assertThat(Math.abs(d1 - d2) < epsilon).isTrue();
    }

    @Test
    void useApacheCommonsMath() {
        double epsilon = 0.000001d;
        assertThat(Precision.equals(d1, d2, epsilon)).isTrue();
    }
}
