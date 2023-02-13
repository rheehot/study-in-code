package junit.library;

import static java.time.Duration.ofMillis;
import static junit.library.AssertUntilTimeout.work;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AssertUntilTimeoutTest {
    @Test
    @DisplayName("assertUntilTimeout() 메서드는 제한시간 내에 원하는 결과를 확인하면 성공합니다")
    void testUntilSuccess() {
        // 테스트 조건 만족 신호로 사용
        AtomicBoolean conditionSatisfied = new AtomicBoolean(false);

        // 의도적으로 일정 시간(N 밀리초) 지연 후 테스트 조건을 만족시킨다.
        Executor delayedExecutor = CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS);
        delayedExecutor.execute(()-> conditionSatisfied.set(true));

        // 테스트 실행
        work(ofMillis(1100), conditionSatisfied::get, conditionSatisfied::toString);
    }
}
