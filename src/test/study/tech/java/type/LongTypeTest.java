package tech.java.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LongTypeTest {

    @Test
    void maxValue () {
        long max = Long.MAX_VALUE;
        Assertions.assertEquals(9_223_372_036_854_775_807L, max);
    }

    @Test
    void minValue () {
        long min = Long.MIN_VALUE;
        Assertions.assertEquals(-9_223_372_036_854_775_808L, min);
    }
}
