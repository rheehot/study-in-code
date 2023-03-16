package tech.java.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtomicLongTest {
    /**
     * 내장된 값을 1증가 시킵니다.
     */
    @Test
    void incrementAndGet() {
        AtomicLong atomicLong = new AtomicLong(0);

        assertEquals(1, atomicLong.incrementAndGet());
        assertEquals(2, atomicLong.incrementAndGet());
    }
}
