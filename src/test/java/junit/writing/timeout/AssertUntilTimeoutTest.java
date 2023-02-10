package junit.writing.timeout;

import static java.time.Duration.ofMillis;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.library.AssertUntilTimeout;
import junit.library.TryUntilTimeout;

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

    @Test
    void statusReflectsCommandedValue_awaitility() {
        // When : send a command to change target value
        fakeController.sendCommand(100);

        // Then : The status reflects the commanded value within timeout
        await().atMost(2, SECONDS).until(() -> fakeController.readStatus() == 100);
        Assertions.assertEquals(100, fakeController.readStatus());
    }
}
