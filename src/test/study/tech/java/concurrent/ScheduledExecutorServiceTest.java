package tech.java.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
public class ScheduledExecutorServiceTest {

    @Test
    void scheduleAtFixedRate() throws InterruptedException {

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(2000);
                log.info("hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(10000);

        /* result
        13:07:30.977 [pool-1-thread-1] INFO tech.javalang.concurrent.ScheduledExecutorServiceTest - hello
        13:07:32.980 [pool-1-thread-1] INFO tech.javalang.concurrent.ScheduledExecutorServiceTest - hello
        13:07:34.981 [pool-1-thread-1] INFO tech.javalang.concurrent.ScheduledExecutorServiceTest - hello
        13:07:36.984 [pool-1-thread-1] INFO tech.javalang.concurrent.ScheduledExecutorServiceTest - hello
        13:07:38.986 [pool-1-thread-1] INFO tech.javalang.concurrent.ScheduledExecutorServiceTest - hello
        */
    }

    @Test
    void continuousExecutionWithoutGap() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        CountDownLatch latch = new CountDownLatch(1);
        executor.scheduleAtFixedRate(() -> {
            try {
                latch.await();
                log.info("hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 200, TimeUnit.MILLISECONDS);

        Thread.sleep(500);
        latch.countDown();
        Thread.sleep(1000);

        /* result
        16:11:12.225 [pool-1-thread-1] INFO tech.java.concurrent.ScheduledExecutorServiceTest - hello
        16:11:12.229 [pool-1-thread-1] INFO tech.java.concurrent.ScheduledExecutorServiceTest - hello
        16:11:12.230 [pool-1-thread-1] INFO tech.java.concurrent.ScheduledExecutorServiceTest - hello
        16:11:12.323 [pool-1-thread-1] INFO tech.java.concurrent.ScheduledExecutorServiceTest - hello
        16:11:12.521 [pool-1-thread-1] INFO tech.java.concurrent.ScheduledExecutorServiceTest - hello
        16:11:12.723 [pool-1-thread-1] INFO tech.java.concurrent.ScheduledExecutorServiceTest - hello
        16:11:12.922 [pool-1-thread-1] INFO tech.java.concurrent.ScheduledExecutorServiceTest - hello
        16:11:13.122 [pool-1-thread-1] INFO tech.java.concurrent.ScheduledExecutorServiceTest - hello
        */
    }

    @Test
    void scheduleWithFixedDelay() throws InterruptedException {

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(2000);
                log.info("hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(10000);
        /* result
        13:08:15.736 [pool-1-thread-1] INFO tech.javalang.concurrent.ScheduledExecutorServiceTest - hello
        13:08:18.741 [pool-1-thread-1] INFO tech.javalang.concurrent.ScheduledExecutorServiceTest - hello
        13:08:21.742 [pool-1-thread-1] INFO tech.javalang.concurrent.ScheduledExecutorServiceTest - hello
        */
    }

    @Test
    void shutdown() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(2000);
                queue.put("message");
                log.info("hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(1000);
        executor.shutdown();

        assertTrue(executor.awaitTermination(2000, TimeUnit.MILLISECONDS));
        assertTrue(executor.isShutdown());
        assertTrue(executor.isTerminated());
        assertEquals("message", queue.poll());
        assertTrue(future.isDone());
        assertTrue(future.isCancelled());
    }


    @Test
    void cancel() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(2000);
                queue.put("message");
                log.info("hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(1000);
        future.cancel(false);

        assertEquals("message", queue.poll());
        assertTrue(future.isDone());
        assertTrue(future.isCancelled());
    }
}
