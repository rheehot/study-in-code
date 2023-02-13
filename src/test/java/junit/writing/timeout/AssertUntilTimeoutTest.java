package junit.writing.timeout;

import static java.time.Duration.ofMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.fieldIn;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.Matchers.equalTo;

import org.awaitility.core.ConditionEvaluationListener;
import org.awaitility.core.EvaluatedCondition;
import org.awaitility.core.TimeoutEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import junit.library.AssertUntilTimeout;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssertUntilTimeoutTest {
    FakeTarget target;

    @BeforeEach
    void setUp() {
        target = new FakeTarget();
        target.connect();
    }

    @Test
    void status_reflects_commanded_value() {
        // 1. When - 상태 변경 명령 전송
        target.send(new ChangeCommand(100), ofMillis(500));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < 2000) {
            if (target.readStatus() == 100) {
                // success
                return;
            }
        }

        // 3. (실패 시) 실패 판정에 사용된 값 출력
        log.info("status : {}", target.readStatus());
        Assertions.fail();
    }

    @Test
    void status_reflects_commanded_value_ours() {
        // 1. When - 상태 변경 명령 전송
        target.send(new ChangeCommand(100), ofMillis(500));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        AssertUntilTimeout.work(ofMillis(2000), // timeout
                                () -> target.readStatus() == 100, // assertOnce
                                () -> "status : " + target.readStatus() // message on failure
        );
    }

    @Test
    void status_reflects_commanded_value_junit() {
        // 1. When - 상태 변경 명령 전송
        target.send(new ChangeCommand(100), ofMillis(500));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        Assertions.assertTimeout(ofMillis(2000), // timeout
                                 () -> {
                                     while (target.readStatus() != 100) { // assert
                                         Thread.sleep(100);
                                     }},
                                 () -> "status : " + target.readStatus() // message on failure
        );
    }

    @Nested
    class Awaitility {

        @Test
        void status_reflects_commanded_value_listener() {
            // 1. When - 상태 변경 명령 전송
            target.send(new ChangeCommand(100), ofMillis(500));

            // 2. Then - 일정 시간 동안 상태 변경 여부 확인
            with().conditionEvaluationListener(condition -> log.info("status on failure : {}", target.readStatus()))
                  .await()
                  .atMost(500, MILLISECONDS)
                  .pollInterval(ofMillis(100))
                  .until(() -> target.readStatus() == 100);
        }

        @Test
        void status_reflects_commanded_value_on_timeout() {
            // 1. When - 상태 변경 명령 전송
            target.send(new ChangeCommand(100), ofMillis(500));

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
            target.send(message, ofMillis(600));

            // Then : The status reflects the commanded value within timeout
            await().atMost(500, MILLISECONDS)
                   .until(fieldIn(target).ofType(Message.class).andWithName("message"), equalTo(message));
        }
    }
}
