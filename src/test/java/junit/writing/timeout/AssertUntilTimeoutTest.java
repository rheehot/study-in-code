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
import junit.library.TryUntilTimeout;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssertUntilTimeoutTest {
    FakeController fakeController;

    @BeforeEach
    void setUp() {
        fakeController = new FakeController();
        fakeController.connect();
    }

    @Test
    void statusReflectsCommandedValue() {
        // When : send a command to change target value
        fakeController.sendCommand(100);

        // Then : The status reflects the commanded value
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < 2000) {
            if (fakeController.readStatus() == 100) {
                // success
                return;
            }
        }

        // fail
        Assertions.fail();
    }

    @Test
    void statusReflectsCommandedValue_ours() {
        // When : send a command to change target value
        fakeController.sendCommand(100);

        // Then : The status reflects the commanded value within timeout
        AssertUntilTimeout.assertUntilTimeout(ofMillis(2000), // timeout
                                              () -> fakeController.readStatus() == 100, // assertOnce
                                              () -> "status : " + fakeController.readStatus() // message on failure
        );
    }

    @Test
    void statusReflectsCommandedValue_ours2() {
        // When : send a command to change target value
        fakeController.sendCommand(100);

        // Then : The status reflects the commanded value within timeout
        boolean result = TryUntilTimeout.work(() -> fakeController.readStatus() == 100, // tryOnce
                                              2000, // timeout
                                              () -> "status : " + fakeController.readStatus() // message on failure
        );
        Assertions.assertTrue(result);
    }

    @Test
    void statusReflectsCommandedValue_junit() {
        // When : send a command to change target value
        fakeController.sendCommand(100);

        // Then : The status reflects the commanded value within timeout
        Assertions.assertTimeoutPreemptively(ofMillis(2000), // timeout
                                             () -> {
                                                 while (fakeController.readStatus() != 100) {
                                                     // sleep if needed
                                                 }
                                             }, () -> "status : " + fakeController.readStatus() // message on failure
        );
    }

    @Nested
    class Awaitility {

        @Test
        void statusReflectsCommandedValue_awaitility1() {
            // When : send a command to change target value
            fakeController.sendCommand(100, ofMillis(600));

            // Then : The status reflects the commanded value within timeout
            await().atMost(500, MILLISECONDS).until(() -> fakeController.readStatus() == 100);
        }

        @Test
        void evaluationListener() {
            // When : send a command to change target value
            fakeController.sendCommand(100, ofMillis(300));

            // Then : The status reflects the commanded value within timeout
            with().conditionEvaluationListener(condition -> log.info("status on failure : {}", fakeController.readStatus()))
                  .await()
                  .atMost(500, MILLISECONDS)
                  .until(() -> fakeController.readStatus() == 100);
        }

        @Test
        void evaluationListener_onTimeout() {
            // When : send a command to change target value
            fakeController.sendCommand(100, ofMillis(600));

            // Then : The status reflects the commanded value within timeout
            with().conditionEvaluationListener(new ConditionEvaluationListener<>() {
                @Override
                public void onTimeout(TimeoutEvent timeoutEvent) {
                    log.info("status of failure : {}", fakeController.readStatus());
                }

                @Override
                public void conditionEvaluated(EvaluatedCondition condition) {}

            }).await().atMost(500, MILLISECONDS).until(() -> fakeController.readStatus() == 100);
        }

        @Test
        void statusReflectsCommandedValue_expected() {
            // When : send a command to change target value
            fakeController.sendCommand(100);

            // Then : The status reflects the commanded value within timeout
            await().atMost(100, MILLISECONDS)
                   .until(() -> fakeController.readStatus() == 100);
//                   .messageOnFailure(() -> "status : " + fakeController.readStatus());
        }

        @Test
        void statusReflectsCommandedValue_int() {
            // When : send a command to change target value
            fakeController.sendCommand(100, ofMillis(600));

            // Then : The status reflects the commanded value within timeout
            await().atMost(500, MILLISECONDS).until(fieldIn(fakeController).ofType(int.class).andWithName("value"),
                                                    equalTo(100));
        }

        @Test
        void statusReflectsCommandedValue_awaitility2() {
            // When : send a command to change target value
            Message message = new Message("Joo", 100);
            fakeController.sendCommand(message, ofMillis(600));

            // Then : The status reflects the commanded value within timeout
            await().atMost(500, MILLISECONDS).until(fieldIn(fakeController).ofType(Message.class).andWithName("message"), equalTo(message));
        }
    }
}
