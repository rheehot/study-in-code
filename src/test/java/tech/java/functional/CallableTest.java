package tech.java.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

public class CallableTest {
    /**
     * Callable 인터페이스 구현에서 실행 중 발생할 수 있는 체크 예외를 처리하지 않아도 됩니다.
     */
    @Test
    void doNotHandleCheckedException() throws Exception {
        Callable<Integer> callable = () -> {
            Thread.sleep(1000); // 예외를 잡아서 처리하지 않아도 됩니다.
            return 0;
        };
        Assertions.assertEquals(0, callable.call());
    }

    /**
     * Callable 인터페이스 실행 중 발생한 예외는 call() 메서드 호출 시 발생합니다.
     */
    @Test
    void throwException() {
        Callable<Integer> callable = () -> {
            throw new Exception();
        };
        Assertions.assertThrows(Exception.class, callable::call);
    }
}
