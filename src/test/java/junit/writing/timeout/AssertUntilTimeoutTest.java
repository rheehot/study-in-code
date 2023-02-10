package junit.writing.timeout;

import static java.time.Duration.ofMillis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.library.AssertUntilTimeout;

public class AssertUntilTimeoutTest {
    Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
        controller.connect();
    }

    @Test
    void statusReflectsCommandedValue() {
        // When : send a command to change target value
        controller.sendCommand(100);

        // Then : The status reflects the commanded value
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < 2000) {
            if (controller.readStatus() == 100) {
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
        controller.sendCommand(100);

        // Then : The status reflects the commanded value within timeout
        AssertUntilTimeout.assertUntilTimeout(ofMillis(2000), // timeout
                                              () -> controller.readStatus() == 100, // assertOnce
                                              () -> "status : " + controller.readStatus() // message on failure
        );
    }

    @Test
    void statusReflectsCommandedValue_junit() {
        // When : send a command to change target value
        controller.sendCommand(100);

        // Then : The status reflects the commanded value within timeout
        Assertions.assertTimeoutPreemptively(ofMillis(2000), // timeout
                                             () -> {
                                                 while (controller.readStatus() != 100); // executable
                                             }, () -> "status : " + controller.readStatus() // message on failure

        );
    }
}
