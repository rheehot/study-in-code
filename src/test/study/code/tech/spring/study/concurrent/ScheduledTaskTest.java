package code.tech.spring.study.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledTaskTest {
    @Test
    void cancelScheduledExecutor() throws InterruptedException {
        // Given
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // When : 타임 스탬프 태스크 시작 - 대기 - 취소 - 대기
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() ->
                log.info("hello"), 0, 100, TimeUnit.MILLISECONDS);
        Thread.sleep(5000);
        future.cancel(true);
        Assertions.assertTrue(future.isCancelled());
        Assertions.assertTrue(future.isDone());
        Thread.sleep(3000);
    }


}
