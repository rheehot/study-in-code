package tech.java.concurrent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static code.util.SleepUtil.sleep;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

public class CompletableFutureTest {

    @Nested
    class AllOf {
        List<CompletableFuture<Boolean>> futures;

        @BeforeEach
        void setUp() {
            futures = List.of(
                    CompletableFuture.supplyAsync(() -> {
                        sleep(500);
                        return true;
                    }),
                    CompletableFuture.supplyAsync(() -> {
                        sleep(1000);
                        return false;
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

        /**
         * Steam API를 활용해 여러 Future의 결과를 하나로 조합합니다.
         */
        @Test
        void combineFutureResults() {
            await().during(2000,TimeUnit.MILLISECONDS)
                    .until(() -> {
                        return !futures.stream()
                                .map(future -> future.join())
                                .reduce((a, b) -> a && b)
                                .orElse(false);
                    });
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
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                throw new RuntimeException("Hello Exception");
            });

            assertThrows(ExecutionException.class, future::get);
            try {
                future.get();
            } catch (ExecutionException e) {
                assertSame(RuntimeException.class, e.getCause().getClass());
            }
        }

        /**
         * completeExceptionally()로 null을 전달하면 NullPointException이 발생하고 작업은 미완료 상태로 남게 됩니다.
         */
        @Test
        void completeExceptionallyWithNull() {
            CompletableFuture<Boolean> future = new CompletableFuture<>();
            CompletableFuture.runAsync(() -> {
                try {
                    // When: completeExceptionally()로 null을 전달
                    future.completeExceptionally(null); // will throw NullPointException
                } catch (Exception e) {
                    // Then: NullPointException이 발생
                    assertSame(NullPointerException.class, e.getClass());
                }
            });

            // Then: 작업은 미완료 상태로 남게 됩니다.
            assertThrows(TimeoutException.class, () -> future.get(1000, TimeUnit.MILLISECONDS));
            assertFalse(future.isCompletedExceptionally());
            assertFalse(future.isDone());
            assertFalse(future.isCancelled());
        }
    }
}
