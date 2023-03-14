package tech.java.concurrent;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;

import java.util.concurrent.*;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * 쓰레드 풀의 상태에 따라 달라지는 동작을 시간의 흐름을 따라 확인합니다.
 *
 * 각 단계를 테스트 메서드로 나누고 순서대로 실행되도록 설정합니다. 메서드 간 쓰레드 풀 상태를 공유하기
 * 위해서 클래스 단위로 테스트 인스턴스를 생성해 공유하게 합니다.
 */
@TestInstance(Lifecycle.PER_CLASS) // 메서드 간 상태 공유
@TestMethodOrder(OrderAnnotation.class) // 메서드 순서 지정
public class ThreadPoolLifeCycleTest {
    // 쓰레드 풀
    ThreadPoolExecutor threadPool;
    // 1초간 실행되는 무거운 작업 시뮬레이션
    Callable<Boolean> heavyTask;
    // 시나리오 상에서 작업 Future들
    Future<Boolean> future1;
    Future<Boolean> future2;
    Future<Boolean> future3;
    Future<Boolean> future4;

    @BeforeAll
    void setUp() {
        // 1초간 실행되는 무거운 작업 시뮬레이션
        heavyTask = () -> {
            Thread.sleep(1000);
            return true;
        };

        // 비동기 테스트 프레임워크(Awaitility) 설정
        Awaitility.setDefaultPollInterval(10, TimeUnit.MILLISECONDS);
    }

    /**
     * 쓰레드 풀은 쓰레드를 미리 생성하지 않고, 요청된 시점에 생성하는 게으른(Lazy)한 특성을 가집니다.
     */
    @Test
    @Order(0)
    void createLazyThreadPool() {
        final int corePoolSize = 2;
        final int maximumPoolSize = 3;
        final long keepAliveTime = 1L;
        final int queueCapacity = 1;

        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueCapacity));

        assertEquals(0, threadPool.getPoolSize());
        assertEquals(0, threadPool.getQueue().size());
    }

    /**
     * 첫 번째 작업이 제출 될 때 쓰레드 풀의 첫 번째 쓰레드가 생성되어 작업이 할당됩니다.
     */
    @Test
    @Order(1)
    void createFirstThread() {
        future1 = threadPool.submit(heavyTask);

        assertEquals(1, threadPool.getPoolSize());
        assertEquals(0, threadPool.getQueue().size());
    }

    /**
     * 쓰레드 풀에 생성된 쓰레드 개수가 기본치(coreThreadPool) 보다 적은 경우 새로운
     * 쓰레드를 생성하고 요청된 작업을 할당합니다.
     */
    @Test
    @Order(2)
    void reachToCorePoolSize() throws InterruptedException {
        future2 = threadPool.submit(heavyTask);

        assertEquals(2, threadPool.getPoolSize());
        assertEquals(0, threadPool.getQueue().size());
    }

    /**
     * 쓰레드 풀에 생성된 쓰레드 개수가 기본치(coreThreadPool) 이상인 경우 새로운 쓰레드를 생성하지 않고 작업을 큐에 할당합니다.
     * 큐에 할당된 작업은 이전에 생성된 쓰레드가 이전 작업을 완료하면 순차적으로 실행됩니다.
     */
    @Test
    @Order(3)
    void reachToQueueCapacity() {
        future3 = threadPool.submit(heavyTask);

        assertEquals(2, threadPool.getPoolSize());
        assertEquals(1, threadPool.getQueue().size());
    }

    /**
     * 쓰레드 풀에 생성된 쓰레드가 기본치(coreThreadPool) 이상이고 큐가 가득차 있는 경우에는 새로운 쓰레드를 최대치(maximumThreadPool) 까지 생성하고 작업을 할당한다.
     */
    @Test
    @Order(4)
    void reachToMaxPoolSize() {
        future4 = threadPool.submit(heavyTask);

        assertEquals(3, threadPool.getPoolSize());
        assertEquals(1, threadPool.getQueue().size());
    }

    /**
     * 쓰레드 풀의 작업큐가 가득차 있고, 생성된 쓰레드 역시 최대치(maximumThreadPool)인 경우 새로운 작업을 거부합니다.
     */
    @Test
    @Order(5)
    void overMaxPoolSize() {
        assertThrows(
                RejectedExecutionException.class,
                () -> threadPool.submit(heavyTask)
        );
    }

    @Test
    @Order(6)
    void result() {
        // 쓰레드에 직접 할당된 작업은 즉시 실행 됩니다.
        await().atLeast(900, TimeUnit.MILLISECONDS)
                .atMost(1100, TimeUnit.MILLISECONDS)
                .until(() ->
                        future1.isDone() &&
                        future2.isDone() &&
                        future4.isDone()
                );

        // 큐에 할당된 작업은 이전 작업이 완료된 후 순차적으로 실행됩니다.
        await().atLeast(900, TimeUnit.MILLISECONDS)
                .atMost(1100, TimeUnit.MILLISECONDS)
                .until(() ->
                        future3.isDone()
                );

        // 모든 작업 완료 후, keepAliveTime 동안 작업 요청이 없으면 쓰레드 풀의 쓰레드 개수는 기본치(coreThreadPool)로 유지됩니다.
        assertEquals(2, threadPool.getPoolSize());
        assertEquals(0, threadPool.getQueue().size());
    }
}
