package junit;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class AssertTimeoutTest {
    @Test
    @DisplayName("assertTimeout() 메서드는 지정된 시간 내에 원하는 결과를 확인하면 성공합니다")
    void testUntilSuccess() {
        // 테스트 조건 만족 신호로 사용
        AtomicBoolean conditionSatisfied = new AtomicBoolean(false);

        // 의도적으로 일정 시간(N 밀리초) 지연 후 테스트 조건을 만족시킨다.
        Executor delayedExecutor = CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS);
        delayedExecutor.execute(()-> conditionSatisfied.set(true));

        // 테스트 조건이 만족할 때까지 반복 체크하는 Action
        Executable testUntilSuccess = () -> {
            while(!conditionSatisfied.get()) {
                // do nothing
            }
        };
        // 테스트 결과에 반영된 상태를 가시화 하기 위한 정보
        Supplier<String> messageSupplier = () -> "conditionSatisfied = " + conditionSatisfied.get();

        // 테스트 실행
        Assertions.assertTimeout(Duration.ofMillis(2000), testUntilSuccess, messageSupplier);
    }
}
