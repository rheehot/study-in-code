package tech.java.clazz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeCheckTest {

    @Test
    @DisplayName("instanceof 연산자는 런타임 시, 객체의 슈퍼 클래스 및 구현 인터페이스까지 확인할 수 있습니다")
    void instanceOf() {
        Object num = Integer.valueOf(1);

        // 타입 O
        assertTrue(num instanceof Integer); // 상속 클래스
        assertTrue(num instanceof Number);
        assertTrue(num instanceof Object);
        assertTrue(num instanceof Serializable); // 구현 인터페이스
        assertTrue(num instanceof Comparable);

        // 타입 X
        assertFalse(num instanceof Double);
        assertFalse(num instanceof Comparator);
    }
}
