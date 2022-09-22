package spring.study.data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    @Test
    @DisplayName("Enum + 문자열 키 검증")
    void enumStringComposeKey() {
        Map<String, String> map = new HashMap<>();
        map.put(SampleEnum.HI + "A", "HI");
        map.put(SampleEnum.HELLO + "A", "HELLO");

        Assertions.assertEquals("HELLO", map.get(SampleEnum.HELLO + "A"));
    }
}
