package tech.spring.study.data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    @Test
    void getOrDefault() {
        // Given
        Map<String, Integer> userAndAgeMap = new HashMap<>();
        userAndAgeMap.put("Joo", 40);
        userAndAgeMap.put("Yang", 39);

        // Expected
        Assertions.assertEquals(10, userAndAgeMap.getOrDefault("SSa", 10));
        Assertions.assertEquals(40, userAndAgeMap.getOrDefault("Joo", 10));
        Assertions.assertEquals(39, userAndAgeMap.getOrDefault("Yang", 10));
    }

    @Test
    @DisplayName("Enum + 문자열 키 검증")
    void enumStringComposeKey() {
        Map<String, String> map = new HashMap<>();
        map.put(SampleEnum.HI + "A", "HI");
        map.put(SampleEnum.HELLO + "A", "HELLO");

        Assertions.assertEquals("HELLO", map.get(SampleEnum.HELLO + "A"));
    }
}
