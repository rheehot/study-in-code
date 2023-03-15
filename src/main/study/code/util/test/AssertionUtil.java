package code.util.test;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class AssertionUtil {

    public static void assertIntervalWithRange(LocalTime startInclusive, LocalTime endExclusive, long targetMin, long targetMax) {
        long interval = Duration.between(startInclusive, endExclusive).toMillis();
        try {
            assertTrue(interval >= targetMin && interval <= targetMax);
        } catch (AssertionError e) {
            System.out.println("interval: " + interval);
            System.out.println("targetMin: " + targetMin);
            System.out.println("targetMax: " + targetMax);
            throw e;
        }
    }

    public static void assertIntervalWithMargin(LocalTime startInclusive, LocalTime endExclusive, long target, long margin) {
        long interval = Duration.between(startInclusive, endExclusive).toMillis();
        try {
            assertTrue(interval >= (target - margin) && interval <= (target + margin));
        } catch (AssertionError e) {
            System.out.println("interval: " + interval);
            System.out.println("target: " + target);
            System.out.println("margin: " + margin);
            throw e;
        }
    }


    private AssertionUtil() {}
}
