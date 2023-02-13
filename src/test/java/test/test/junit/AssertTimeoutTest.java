package test.test.junit;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.data.ChangeCommand;
import test.helper.FakeTarget;

@Slf4j
public class AssertTimeoutTest {
    FakeTarget target;

    @BeforeEach
    void setUp() {
        target = new FakeTarget();
        target.connect();
    }

    @Test
    void status_reflects_commanded_value_junit() {
        // 1. When - 상태 변경 명령 전송
        target.send(new ChangeCommand(100), ofMillis(500));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        assertTimeout(ofMillis(2000), // timeout
                      () -> {
                          while (target.readStatus() != 100) { // assert
                              Thread.sleep(100);
                          }
                      }, () -> "status : " + target.readStatus() // message on failure
        );
    }
}
