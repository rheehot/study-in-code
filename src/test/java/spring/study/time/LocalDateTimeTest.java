package spring.study.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LocalDateTimeTest {
    @Test
    void isBefore() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusNanos(1000);
        Assertions.assertTrue(now.isBefore(future));
    }

    @Test
    void isAfter() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusNanos(1000);
        Assertions.assertTrue(now.isAfter(past));
    }
}
