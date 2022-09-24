package spring.study.java.method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ParameterPassTest {

    @Test
    @DisplayName("1. Primitive int Type passing 변수의 값은 inc() 호출 이후에 변화되지 않습니다")
    void intParameter() {
        int passing = 3; // 원시타입 값
        inc(passing);
        Assertions.assertEquals(3, passing);
    }

    @Test
    @DisplayName("2. Integer Type(Immutable) passing 객체가 가지는 정수 값은 inc() 호출 이후에 변화되지 않습니다")
    void integerObjectParameter() {
        Integer passing = 3; // Immutable 객체 참조 (값)
        inc(passing);
        Assertions.assertEquals(3, passing);
    }

    @Test
    @DisplayName("3. AtomicInteger Type(Thread-safe&Mutable) passing 객체가 가지는 정수 값은 inc() 호출 이후에 1증가 합니다")
    void atomicIntegerParameter() {
        AtomicInteger passing = new AtomicInteger(3); // Mutable & Thread-safe 객체 참조 (값)
        inc(passing);
        Assertions.assertEquals(4, passing.get());
    }

    void inc(int passed) {
        passed++;
    }

    void inc(Integer passed) {
        passed++; // 불변 객체
    }

    void inc(AtomicInteger passed) {
        passed.incrementAndGet();
    }

}
