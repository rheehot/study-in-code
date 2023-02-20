package code.test.test.junit;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import code.test.data.ChangeInventory;
import code.test.helper.FakeStore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JunitAssertTimeoutTest {
    FakeStore store;

    @BeforeEach
    void setUp() {
        store = new FakeStore();
        store.init(ofMillis(500));
    }

    @Test
    void inventory_reflects_requested_value() {
        // 1. When - 상태 변경 명령 전송
        store.requestAsync(new ChangeInventory(100));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        assertTimeout(ofMillis(2000), // timeout
                      () -> {
                          while (store.getInventory() != 100) { // condition
                              Thread.sleep(100);
                          }
                      }, () -> "status : " + store.getInventory() // message on failure
        );
    }
}
