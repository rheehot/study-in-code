package code.util.test;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public final class AssertionUtil {
    private static boolean logAvailable;

    public static void setLogAvailable(boolean available) {
        logAvailable = available;
    }

    public static void assertDurationWithRange(LocalTime startInclusive, LocalTime endExclusive, long targetMin, long targetMax) {
        long duration = Duration.between(startInclusive, endExclusive).toMillis();
        try {
            assertTrue(duration >= targetMin && duration <= targetMax);
            if (logAvailable) {
                log.info("duration: {}, targetMin: {}, targetMax: {}", duration, targetMin, targetMax);
            }
        } catch (AssertionError e) {
            log.info("AssertionError - duration: {}, targetMin: {}, targetMax: {}", duration, targetMin, targetMax);
            throw e;
        }
    }

    public static void assertDurationWithMargin(LocalTime startInclusive, LocalTime endExclusive, long target, long margin) {
        long duration = Duration.between(startInclusive, endExclusive).toMillis();
        try {
            assertTrue(duration >= (target - margin) && duration <= (target + margin));
            if (logAvailable) {
                log.info("duration: {}, target: {}, margin: {}", duration, target, margin);
            }
        } catch (AssertionError e) {
            log.info("AssertionError - duration: {}, target: {}, margin: {}", duration, target, margin);
            throw e;
        }
    }

    private AssertionUtil() {}
}
