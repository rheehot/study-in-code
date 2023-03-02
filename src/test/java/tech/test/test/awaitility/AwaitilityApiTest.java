package tech.test.test.awaitility;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.with;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("CodeBlock2Expr")
@Slf4j
public class AwaitilityApiTest {
    /**
     * atLeast() 메서드를 사용해 최소한 N 시간 이후에 조건을 만족하는지 확인할 수 있다.
     * atLeastSuccess() 메서드는 N 시간 이후에 조건을 만족하여 성공하는 케이스를 보여준다.
     */
    @Test
    void atLeastSuccess() {
        // Given
        AtomicInteger value = new AtomicInteger(0);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // When: 1초 후에 조건 만족하도록 설정
        executor.submit(() -> {
            Thread.sleep(1000);
            value.set(1);
            return true;
        });

        // Then: 1초 이후에 조건을 만족하여 성공
        with().conditionEvaluationListener(condition -> log.info("value: {}", condition))
                .await().atLeast(1000, TimeUnit.MILLISECONDS).until(() -> value.get() == 1);
    }

    /**
     * atLeast() 메서드를 사용해 최소한 N 시간 이후에 조건을 만족하는지 확인할 수 있다.
     * atLeastFail()  메서드는 N 시간 이전에 조건이 만족하여 실패하는 케이스를 보여준다.
     */
    @Test
    void atLeastFail() {
        // Given
        AtomicInteger value = new AtomicInteger(0);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // When: 1초 후에 조건 만족하도록 설정
        executor.submit(() -> {
            Thread.sleep(1000);
            value.set(1);
            return true;
        });

        // Then: 1.1초 이전에 조건을 만족하여 실패
        Assertions.assertThrows(ConditionTimeoutException.class, () -> {
            with().conditionEvaluationListener(condition -> log.info("value: {}", condition))
                    .await().atLeast(1100, TimeUnit.MILLISECONDS).until(() -> value.get() == 1);
        });
    }

    /**
     * untilAsserted() 메서드를 사용하면 조건 계산식이 boolean 결과를 반환하지 않고 직접 Assertion이 가능하다.
     */
    @Test
    void untilAsserted() {
        // Given
        AtomicInteger value = new AtomicInteger(0);
        ExecutorService executor = Executors.newSingleThreadExecutor();

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
