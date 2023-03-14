package code.util.test;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class AssertionUtil {

    public static void assertDurationWithRange(LocalTime startInclusive, LocalTime endExclusive, long targetMin, long targetMax) {
        long duration = Duration.between(startInclusive, endExclusive).toMillis();
        try {
            assertTrue(duration >= targetMin && duration <= targetMax);
        } catch (AssertionError e) {
            System.out.println("duration: " + duration);
            System.out.println("targetMin: " + targetMin);
            System.out.println("targetMax: " + targetMax);
            throw e;
        }
    }

    public static void assertDurationWithMargin(LocalTime startInclusive, LocalTime endExclusive, long target, long margin) {
        long duration = Duration.between(startInclusive, endExclusive).toMillis();
        try {
            assertTrue(duration >= (target - margin) && duration <= (target + margin));
        } catch (AssertionError e) {
            System.out.println("duration: " + duration);
            System.out.println("target: " + target);
            System.out.println("margin: " + margin);
            throw e;
        }
    }


    private AssertionUtil() {}
}
