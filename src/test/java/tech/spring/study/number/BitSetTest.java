package tech.spring.study.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.stream.IntStream;

public class BitSetTest {
    @Test
    void checkBit() {
        // Given
        String input = "00008000";
        long parsed = Long.parseLong(input, 16);
        BitSet bitSet = BitSet.valueOf(new long[] { parsed });

        // Expected
        Assertions.assertEquals(0x00008000, parsed); // 문자열 16진수 -> 정수 변환 확인
        Assertions.assertTrue(bitSet.get(15)); // 15번째 비트 1인지 확인
        IntStream.range(0, 32)                 // 나머지 비트는 모두 0인지 확인
                 .forEach(index -> {
                     if (index != 15) {
                         Assertions.assertFalse(bitSet.get(index));
                     }
                 });
    }
}
