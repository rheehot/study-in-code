package junit.writing.timeout;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import junit.library.AssertUntilTimeout;

public class SelfAssertUntilTimeoutTest {
    Status lastStatus;
    Controller controller;

    @BeforeAll
    void setUp() {
        lastStatus = new Status();
        channel = new Channel(lastStatus);
        channel.connect().sync();
    }

    @Test
    void changeStatusReflectsLastStatus() {
        // When : send a command
        channel.send(new ChangeStatus(100));

        // Then : assert until it receives the expected result within timeout
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < 2000) {
            if (lastStatus.getStatus() == 100) {
                // success
                return;
            }
        }

        // fail
        Assertions.fail();
    }

    @Test
    void changeStatusReflectsLastStatus_ourLibraryVersion() {
        // When : send a command
        channel.send(new ChangeStatus(100));

        // Then : assert until it receives the expected result within timeout
        AssertUntilTimeout.assertUntilTimeout(ofMillis(2000),
                                              () -> lastStatus.getStatus() == 100,
                                              () -> "status : " + lastStatus.getStatus());
    }

    @Test
    void changeStatusReflectsLastStatus_assertTimeoutVersion() {
        // When : send a command
        channel.send(new ChangeStatus(100));

        // Then : assert until it receives the expected result within timeout
        Assertions.assertTimeout(ofMillis(2000),
                                 () -> {
                                     while (lastStatus.getStatus() != 100) {
                                         // interval if needed
                                     }
                                 }, () -> "status : " + lastStatus.getStatus());
    }
}
