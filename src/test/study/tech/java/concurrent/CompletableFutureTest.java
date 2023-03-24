package tech.java.concurrent;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class CompletableFutureTest {

    @Nested
    class ExceptionHandle {
        /**
         * handle() 메서드를 사용해 비동기 작업에서 발생한 런타임 작업을 try-catch과 다른 형태로 다룰 수 있습니다.
         */
        @Test
        void handleRuntimeException() {
            CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException("Hello Exception");

            }).handle((result, throwable) -> {
                assertNull(result);
                assertEquals("Hello Exception", throwable.getMessage());
                return result;
            });
        }

        /**
         * get() 메서드를 호출하면 비동기 작업 중 발생한 런타임 예외가 다시 던져집니다.
         */
        @Test
        void getThrowException() throws Exception {
            assertThrows(ExecutionException.class, () -> {
                CompletableFuture.runAsync(() -> {
                    throw new RuntimeException("Hello Exception");
                }).get();
            });
        }
    }
}
