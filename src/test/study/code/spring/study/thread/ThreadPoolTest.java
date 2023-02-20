package code.spring.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * <a href="https://www.baeldung.com/thread-pool-java-and-guava">Introduction to Thread Pools in Java</a> 사이트를 학습합니다.
 */
@Slf4j
public class ThreadPoolTest {
    @Test
    void SingleThreadExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> log.info("Hello Thread"));
    }
}
