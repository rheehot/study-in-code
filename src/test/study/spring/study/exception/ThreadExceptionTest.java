package spring.study.exception;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadExceptionTest {

    @Test
    @DisplayName("쓰레드에서 RuntimeException을 던져도 메인 쓰레드 실행에 영향을 주지 않습니다.")
    void notHandleRuntimeExceptionTest() throws InterruptedException {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            log.info("NullPointerException will be thrown.");
            throw new NullPointerException();
        });
        Thread.sleep(1000);
        Assertions.assertTrue(true);
        log.info("Completed");
    }

    @Test
    @DisplayName("쓰레드에서 Error를 던져도 메인 쓰레드 실행에 영향을 주지 않습니다.")
    void notHandleSystemError() throws InterruptedException {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            log.info("OutOfMemoryError will be thrown.");
            throw new OutOfMemoryError();
        });
        Thread.sleep(1000);
        Assertions.assertTrue(true);
        log.info("Completed");
    }
}
