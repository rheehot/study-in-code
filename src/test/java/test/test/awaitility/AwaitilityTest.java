package test.test.awaitility;

import static java.time.Duration.ofMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.fieldIn;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.Matchers.equalTo;

import org.awaitility.core.ConditionEvaluationListener;
import org.awaitility.core.EvaluatedCondition;
import org.awaitility.core.TimeoutEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.data.ChangeCommand;
import test.data.Message;
import test.helper.FakeTarget;

@Slf4j
public class AwaitilityTest {
    FakeTarget target;

    @BeforeEach
    void setUp() {
        target = new FakeTarget();
        target.connect();
    }

    @Test
    void status_reflects_commanded_value_awaitility() {
        // 1. When - 상태 변경 명령 전송
        target.send(new ChangeCommand(100), ofMillis(100));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        with().conditionEvaluationListener(condition -> log.info("status on failure : {}", target.readStatus())) // 테스트 조건 확인 시, 상태 값을 출력합니다
              .await() // 기다립니다
              .atMost(500, MILLISECONDS) // 500 msec 동안
              .pollInterval(ofMillis(100)) // 100 msec 간격으로
              .until(() -> target.readStatus() == 100); // 다음 조건을 통과할 때까지
    }

    @Test
    void status_reflects_commanded_value_on_timeout() {
        // 1. When - 상태 변경 명령 전송
        target.send(new ChangeCommand(100), ofMillis(100));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        with().conditionEvaluationListener(new ConditionEvaluationListener<>() {
            @Override
            public void onTimeout(TimeoutEvent timeoutEvent) {
                log.info("status of failure : {}", target.readStatus());
            }

            @Override
            public void conditionEvaluated(EvaluatedCondition condition) {}

        }).await().atMost(500, MILLISECONDS).pollInterval(ofMillis(100)).until(() -> target.readStatus() == 100);
    }

    @Test
    void status_reflects_commanded_value_field_handling() {
        // When : send a command to change target value
        Message message = new Message("Joo", 100);
        target.send(message, ofMillis(100));

        // Then : The status reflects the commanded value within timeout
        await().atMost(500, MILLISECONDS)
               .until(fieldIn(target).ofType(Message.class).andWithName("message"), equalTo(message));
    }
}
