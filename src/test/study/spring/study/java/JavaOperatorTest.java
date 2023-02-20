package spring.study.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JavaOperatorTest {
    @Test
    @DisplayName("후위 증감연산자는 Expression 처리 이후 증가합니다")
    void plusplusTest() {
        int i = 0;
        Assertions.assertEquals(0, i++);
        Assertions.assertEquals(1, i++);
        Assertions.assertEquals(2, i);
    }
}
