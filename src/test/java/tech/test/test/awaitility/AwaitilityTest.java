package tech.test.test.awaitility;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.core.ConditionEvaluationListener;
import org.awaitility.core.EvaluatedCondition;
import org.awaitility.core.TimeoutEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.test.data.ChangeInventory;
import tech.test.data.Message;
import tech.test.helper.FakeStore;

import static java.time.Duration.ofMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.*;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class AwaitilityTest {
    FakeStore store;

    @BeforeEach
    void setUp() {
        store = new FakeStore();
        store.init(ofMillis(100));
    }

    @Test
    void inventory_reflects_requested_value_awaitility() {
        // 1. When - 상태 변경 명령 전송
        store.requestAsync(new ChangeInventory(100));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        with().conditionEvaluationListener(condition -> log.info("status on failure : {}", store.getInventory())) // 테스트 조건 확인 시, 상태 값을 출력합니다
              .await() // 기다립니다
              .atMost(500, MILLISECONDS) // 500 msec 동안
              .pollInterval(ofMillis(100)) // 100 msec 간격으로
              .until(() -> store.getInventory() == 100); // 다음 조건을 통과할 때까지
    }

    @Test
    void inventory_reflects_requested_value_on_timeout() {
        // 1. When - 상태 변경 명령 전송
        store.requestAsync(new ChangeInventory(100));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        with().conditionEvaluationListener(new ConditionEvaluationListener<>() {
            @Override
            public void onTimeout(TimeoutEvent timeoutEvent) {
                log.info("status of failure : {}", store.getInventory());
            }

            @Override
            public void conditionEvaluated(EvaluatedCondition condition) {}

        }).await().atMost(500, MILLISECONDS).pollInterval(ofMillis(100)).until(() -> store.getInventory() == 100);
    }

    @Test
    void inventory_reflects_requested_value_field_handling() {
        // When : send a command to change target value
        Message message = new Message("Joo", 100);
        store.requestAsync(message);

        // Then : The status reflects the commanded value within timeout
        await().atMost(500, MILLISECONDS)
               .until(fieldIn(store).ofType(Message.class).andWithName("message"), equalTo(message));
    }
}
