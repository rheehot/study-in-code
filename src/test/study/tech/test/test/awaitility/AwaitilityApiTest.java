package tech.test.test.awaitility;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.with;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("CodeBlock2Expr")
@Slf4j
public class AwaitilityApiTest {
    final ExecutorService executor = Executors.newSingleThreadExecutor();

    @BeforeEach
    void setUp(){
        Awaitility.setDefaultPollInterval(1, TimeUnit.MILLISECONDS);
    }

    /**
     * atMost() 메서드는 특정 조건이 일정 시간 이내에 만족하는지 확인합니다.
     */
    @Nested
    class AtMost {
        @Test
        void atMostSuccess() {
            AtomicInteger value = new AtomicInteger(0);

            // When: 1초 후에 조건 만족하도록 설정
            executor.submit(() -> {
                Thread.sleep(1000);
                value.set(1);
                return true;
            });

            // Then: 2초 이내에 조건을 만족하여 성공
            await().atMost(2000, TimeUnit.MILLISECONDS)
                    .until(() -> value.get() == 1);
        }

        @Test
        void atMostFail() {
            AtomicInteger value = new AtomicInteger(0);

            // When: 1초 후에 조건 만족하도록 설정
            executor.submit(() -> {
                Thread.sleep(1000);
                value.set(1);
                return true;
            });

            // Then: 500msec 이내에 조건을 만족하지 못해 예외 발생
            assertThrows(ConditionTimeoutException.class, () -> {
                await().atMost(500, TimeUnit.MILLISECONDS)
                        .until(() -> value.get() == 1);
            });
        }
    }

    /**
     * atLeast() 메서드는 특정 조건이 일정 시간 이후에 만족하는지 확인합니다.
     * 일정 시간 이전에 조건을 만족해서는 안되는 조건을 함께 확인합니다.
     */
    @Nested
    class AtLeast {
        @Test
        void atLeastSuccess() {
            AtomicInteger value = new AtomicInteger(0);

            // When: 1초 후에 조건 만족하도록 설정
            executor.submit(() -> {
                Thread.sleep(1000);
                value.set(1);
                return true;
            });

            // Then: 1초 이후에 조건을 만족하여 성공
            await().atLeast(900, TimeUnit.MILLISECONDS)
                    .until(() -> value.get() == 1);
        }

        @Test
        void atLeastFail() {
            AtomicInteger value = new AtomicInteger(0);

            // When: 1초 후에 조건 만족하도록 설정
            executor.submit(() -> {
                Thread.sleep(1000);
                value.set(1);
                return true;
            });

            // Then: 1.1초 이전에 조건을 만족하여 예외 발생
            assertThrows(ConditionTimeoutException.class, () -> {
                await().atLeast(1100, TimeUnit.MILLISECONDS)
                        .until(() -> value.get() == 1);
            });
        }
    }


    /**
     * during 메서드는 특정 조건이 일정 시간 동안 연속해서 만족하는지 확인합니다.
     */
    @Nested
    class During {
        @Test
        void duringSuccess() {
            AtomicInteger value = new AtomicInteger(0);

            // When: 500ms 동안만 조건을 만족하는 작업 실행
            executor.submit(() -> {
                value.set(1);
                Thread.sleep(500);
                value.set(0);
                Thread.sleep(500);
                return true;
            });

            // Then: 약 500ms 동안 조건을 만족
            await().during(400, TimeUnit.MILLISECONDS)
                    .atMost(1000, TimeUnit.MILLISECONDS)
                    .until(() -> value.get() == 1);
        }

        @Test
        void duringFail() {
            // Given
            AtomicInteger value = new AtomicInteger(0);

            // When: 300ms 동안만 조건을 만족하는 작업 실행
            executor.submit(() -> {
                value.set(1);
                Thread.sleep(300);
                value.set(0);
                Thread.sleep(700);
                return true;
            });

            // Then: 약 500ms 동안 조건 만족 실패
            assertThrows(ConditionTimeoutException.class, () -> {
                await().during(400, TimeUnit.MILLISECONDS)
                        .atMost(1000, TimeUnit.MILLISECONDS)
                        .until(() -> value.get() == 1);
            });
        }
    }

    /**
     * untilAsserted() 메서드를 사용하면 조건 계산식이 boolean 결과를 반환하지 않고 직접 Assertion이 가능하다.
     */
    @Test
    void untilAsserted() {
        // Given
        AtomicInteger value = new AtomicInteger(0);

        // When: 1초 후에 조건 만족하도록 설정
        executor.submit(() -> {
            Thread.sleep(1000);
            value.set(1);
            return true;
        });

        // Then: 2초 안에 조건 만족하는지 Assertion
        with().conditionEvaluationListener(condition -> log.info("value: {}", condition))
                .await().atMost(2000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> assertEquals(1, value.get()));
    }
}
