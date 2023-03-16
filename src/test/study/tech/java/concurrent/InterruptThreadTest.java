package tech.java.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static org.awaitility.Awaitility.await;

/**
 * 쓰레드 인터럽트 메커니즘을 학습합니다.
 *
 * 참고: https://www.baeldung.com/java-interrupted-exception
 */
@Slf4j
public class InterruptThreadTest {

    /**
     * 인터럽트를 처리하지 않는 작업 쓰레드를 인터럽트하면 작업 쓰레드는 살아서 실행을 이어갑니다.
     * 단지, 쓰레드 내부에 인터럽트 플래그만 설정될 뿐입니다.
     */
    @Test
    void Uninterruptibly() throws InterruptedException {
        AtomicBoolean stopSwitch = new AtomicBoolean(false);
        AtomicLong counter = new AtomicLong(0);

        // When: 인터럽트를 처리하지 않는 쓰레드 생성
        Thread thread = new Thread(() -> {
            while(!stopSwitch.get()) {
                counter.incrementAndGet();
            }
        });
        thread.start();

        // When: 쓰레드 인터럽트
        thread.interrupt();

        // Then: 쓰레드는 인터럽트 플래그가 설정된 상태로 살아서 실행을 이어간다.
        await().during(3, TimeUnit.SECONDS)
                .until(() -> thread.isInterrupted() &&
                        thread.isAlive() &&
                        runningThread(counter, 100)
                );
    }

    /**
     * 인터럽트를 처리하는 작업 쓰레드를 인터럽트하면 작업 쓰레드는 인터럽트되어 실행을 종료한다.
     */
    @Test
    void Interruptibly() throws InterruptedException {
        AtomicLong counter = new AtomicLong(0);

        // When: 인터럽트를 처리하는 쓰레드 생성
        Thread thread = new Thread(() -> {
            while(!Thread.interrupted()) { // 인터럽트가 발생하면 인터럽트 플래그를 초기화하고 true를 반환한다.
                counter.incrementAndGet();
            }
            Thread.currentThread().interrupt(); // 인터럽트 플래그를 다시 설정한다. (외부에 인터럽트에 반응 했음을 알림)
        });
        thread.start();

        // When: 쓰레드 인터럽트
        thread.interrupt();

        // Then: 쓰레드는 인터럽트 되고, 실행을 중료한다.
        await().atMost(1, TimeUnit.SECONDS)
                .until(() -> thread.isInterrupted() &&
                        !thread.isAlive() &&
                        !runningThread(counter, 100)
                );
    }

    /**
     * ExecutorService.cancel(true) 메서드는, 비동기 작업에 인터럽트를 요청한다.
     */
    @Test
    void cancelRaiseInterrupt() throws Exception {
        // Given: 1초간 대기하는 비동기 작업을 실행한다.
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executorService.submit(() -> {
            Thread.sleep(1000);
            return true;
        });

        // When: 작업 시작을 보장한 후, 작업을 인터럽트한다.
        Thread.sleep(500);
        future.cancel(true);

        // Then: Future::get 메서드를 통해 비동기 작업 중 발생한 예외를 캐치하고, 세부적인 예외 타입까지 확인 가능하다.
        try {
            future.get(3000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            Assertions.assertSame(InterruptedException.class, e.getCause().getClass());
        }
    }

    /**
     * 작업 쓰레드가 실행을 이어가고 있는지 판단합니다.
     *
     * 이를 위해서는 작업 쓰레드 내부에서 counter 값을 계속 증가시키고 있어야 합니다.
     */
    private boolean runningThread(AtomicLong counter, long checkInterval) throws InterruptedException {
        long before = counter.get();
        Thread.sleep(checkInterval);
        long after = counter.get();
        return before != after;
    }
}
