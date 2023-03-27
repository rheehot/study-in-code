package tech.java.concurrent;

import code.util.SleepUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

public class CompletableFutureTest {

    @Nested
    class AllOf {
        List<CompletableFuture<Void>> futures;

        @BeforeEach
        void setUp() {
            futures = List.of(
                    CompletableFuture.runAsync(() -> {
                        SleepUtil.sleep(500);
                    }),
                    CompletableFuture.runAsync(() -> {
                        SleepUtil.sleep(1000);
                    })
            );
        }

        /**
         * CompletableFuture allOf()를 사용해 여러 개의 비동기 작업 완료를 기다립니다.
         */
        @Test
        void combineFutureByCompletable() {
            await().atLeast(900, TimeUnit.MILLISECONDS)
                    .until(() -> CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).isDone());
        }

        /**
         * Stream allMatch()를 사용해 여러 개의 비동기 작업 완료를 기다립니다.
         */
        @Test
        void combineFutureByStream() {
            await().atLeast(900,TimeUnit.MILLISECONDS)
                    .until(() -> futures.stream().allMatch(CompletableFuture::isDone));
        }
    }

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
