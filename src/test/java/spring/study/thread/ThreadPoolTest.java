package spring.study.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadPoolTest {
    @Test
    void SingleThreadExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> log.info("Hello Thread"));
    }
}
