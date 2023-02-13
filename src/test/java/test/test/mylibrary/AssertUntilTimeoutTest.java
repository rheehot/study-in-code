package test.test.mylibrary;

import static java.time.Duration.ofMillis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.data.ChangeCommand;
import test.helper.FakeTarget;
import test.junit.library.AssertUntilTimeout;

@Slf4j
public class AssertUntilTimeoutTest {
    FakeTarget target;

    @BeforeEach
    void setUp() {
        target = new FakeTarget();
        target.connect();
    }

    @Test
    void status_reflects_commanded_value_raw() {
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
    void status_reflects_commanded_value_library() {
        // 1. When - 상태 변경 명령 전송
        target.send(new ChangeCommand(100), ofMillis(500));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        AssertUntilTimeout.work(ofMillis(2000), // timeout
                                () -> target.readStatus() == 100, // assertOnce
                                () -> "status : " + target.readStatus() // message on failure
        );
    }

}
