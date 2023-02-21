package code.tech.spring.study.math;

import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("부동소수점 정밀도")
public class PrecisionTest {
    @Nested
    @DisplayName("비교")
    class Compare{
        @Test
        @DisplayName("Precision.equals(0.1, 0.2, 0.1) == true")
        void success() {
            double epsilon = 0.1d;
            Assertions.assertTrue(Precision.equals(0.1, 0.2, epsilon));
        }

        @Test
        @DisplayName("Precision.equals(0.1, 0.3, 0.1) == false")
        void fail() {
            double epsilon = 0.1d;
            Assertions.assertFalse(Precision.equals(0.1, 0.3, epsilon));
        }
    }
}
