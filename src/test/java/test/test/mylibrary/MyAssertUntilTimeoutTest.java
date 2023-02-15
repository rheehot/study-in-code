package test.test.mylibrary;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static test.junit.library.AssertUntilTimeout.assertUntilTimeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.data.ChangeInventory;
import test.helper.FakeStore;

@Slf4j
public class MyAssertUntilTimeoutTest {
    FakeStore store;

    @BeforeEach
    void setUp() {
        store = new FakeStore();
        store.init(ofMillis(500));
    }

    @Test
    void inventory_reflects_requested_value_sync() {
        // 1. When - 상태 변경 명령 전송
        store.requestSync(new ChangeInventory(100));

        // 2. Then - 상태 변경 여부 확인
        assertEquals(100, store.getInventory());
    }

    @Test
    void inventory_reflects_requested_value_spaghetti() {
        // 1. When - 상태 변경 명령 전송
        store.requestAsync(new ChangeInventory(100));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < 2000) {
            if (store.getInventory() == 100) { // condition
                // success
                return;
            }
        }

        // 3. (실패 시) 실패 판정에 사용된 값 출력
        log.info("status : {}", store.getInventory());
        fail();
    }

    @Test
    void inventory_reflects_requested_value_clean() {
        // 1. When - 상태 변경 명령 전송
        store.requestAsync(new ChangeInventory(100));

        // 2. Then - 일정 시간 동안 상태 변경 여부 확인
        assertUntilTimeout(ofMillis(2000), // timeout
                           () -> store.getInventory() == 100, // condition
                           () -> "status : " + store.getInventory() // message on failure
        );
    }
}
