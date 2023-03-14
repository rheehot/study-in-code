package tech.java.concurrent;

import code.util.test.AssertionUtil;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static code.util.test.AssertionUtil.assertDurationWithMargin;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;


/**
 * java.util.concurrent.ScheduledExecutorService 인터페이스를 활용해 일정 주기로 반복되는 작업을 처리할 수 있습니다.
 */
@SuppressWarnings("SimplifiableAssertion")
@Slf4j
public class ScheduledExecutorServiceTest {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Nested
    class Schedule {
        List<LocalTime> taskStartTimes = new CopyOnWriteArrayList<>();
        Consumer<Long> task = workingTime -> {
            try {
                Thread.sleep(workingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        @BeforeEach
        void setUp() {
            Awaitility.setDefaultTimeout(Duration.ofSeconds(20));
            AssertionUtil.setLogAvailable(true);
        }

        /**
         * scheduleAtFixedRate() 메서드를 테스트합니다.
         */
        @Nested
        class AtFixedRate {
            @Test
            void simpleSample() {
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                List<LocalTime> taskStartTimes = new CopyOnWriteArrayList<>();

                // When: 반복 작업 실행 후 일정 횟수 실행 대기
                executor.scheduleAtFixedRate( () -> {
                    taskStartTimes.add(LocalTime.now());
                }, 0, 1000, TimeUnit.MILLISECONDS);
                await().until(() -> taskStartTimes.size() > 5);

                // Then: 작업 간 간격 검증
                IntStream.range(1, taskStartTimes.size()).forEach( i -> { // 약 1초 간격으로 작업 반복
                    long duration = Duration.between(taskStartTimes.get(i - 1), taskStartTimes.get(i)).toMillis();
                    assertTrue(duration > 900 && duration < 1100);
                });
            }

            /**
             * scheduleAtFixedRate()로 period 보다 짧은 실행 시간을 가지는 작업을 요청하면, period 주기로 작업이 반복 실행됩니다.
             */
            @Test
            void withShortTask() throws Exception {
                final long taskExecTime = 100; // 작업 시간
                final long period = 1000; // 반복 주기

                // When: 반복 작업 실행 후 일정 횟수 실행 대기
                executor.scheduleAtFixedRate(() -> {
                    taskStartTimes.add(LocalTime.now());
                    task.accept(taskExecTime);
                }, 0, period, TimeUnit.MILLISECONDS);

                shutdownAfterSomeCycle(executor, taskStartTimes::size, 5);

                // Then: 작업 간 간격 검증
                IntStream.range(1, taskStartTimes.size()).forEach(i -> {
                    assertDurationWithMargin(taskStartTimes.get(i - 1), taskStartTimes.get(i), period, 100);
                });
            }

            /**
             * scheduleAtFixedRate()로 period 보다 오랜 시간이 걸리는 작업을 요청하면, 작업 소요 시간이 반복 주기가 됩니다.
             */
            @Test
            void withLongTask() throws Exception {
                final long taskExecTime = 1500; // 작업 시간
                final long period = 1000; // 반복 주기

                // When: 반복 작업 실행 후 일정 횟수 실행 대기
                executor.scheduleAtFixedRate(() -> {
                    taskStartTimes.add(LocalTime.now());
                    task.accept(taskExecTime);
                }, 0, period, TimeUnit.MILLISECONDS);

                shutdownAfterSomeCycle(executor, taskStartTimes::size, 5);

                // Then: 작업 간 간격 검증
                IntStream.range(1, taskStartTimes.size()).forEach(i ->
                        assertDurationWithMargin(taskStartTimes.get(i - 1), taskStartTimes.get(i), taskExecTime, 100));
            }

            /**
             * scheduleAtFixedRate()로 실행되는 첫 작업이 뒤 이은 N개의 작업 시작 시간 만큼 지연되는 경우, 뒤 이은 N개의 작업은 첫 작업 이후에
             * duration 설정을 무시하고 연속해서 실행됩니다.
             */
            @Test
            void withLongSparkTask() throws InterruptedException {
                final AtomicBoolean longTaskSparked = new AtomicBoolean(false);
                final long taskExecTime = 100; // 작업 시간
                final long longTaskExecTime = 1300; // 긴 작업 시간
                final long period = 1000; // 반복 주기

                // When: 반복 작업 실행 후 일정 횟수 실행 대기
                executor.scheduleAtFixedRate(() -> {
                    taskStartTimes.add(LocalTime.now());
                    // 첫 작업만 오래 걸리는 작업으로 모의
                    executeSparkTask(task, taskExecTime, longTaskExecTime, longTaskSparked);
                }, 0, period, TimeUnit.MILLISECONDS);

                shutdownAfterSomeCycle(executor, taskStartTimes::size, 3);

                // Then: 작업 간 간격 검증
                final long followTaskPeriod = 700; // 뒤 이은 작업 간격
                assertDurationWithMargin(taskStartTimes.get(0), taskStartTimes.get(1), longTaskExecTime/*1300*/, 100);
                assertDurationWithMargin(taskStartTimes.get(1), taskStartTimes.get(2), followTaskPeriod/*700*/, 100);
                assertDurationWithMargin(taskStartTimes.get(2), taskStartTimes.get(3), period/*1000*/, 100);
            }

            /**
             * scheduleAtFixedRate()로 실행되는 첫 작업이 반복 주기를 약간 넘기는 경우, 이어지는 작업은 설정된 주기 보다 약간 작은 주기로
             * 실행됩니다.
             */
            @Test
            void withExtremeLongSparkTask() throws InterruptedException {
                final AtomicBoolean longTaskSparked = new AtomicBoolean(false);
                final long taskExecTime = 100; // 작업 시간
                final long period = 1000; // 반복 주기
                final int shiftTaskCount = 5; // 지연 시킬 이어지는 작업 개수
                final long extremeLongJobExecTime = period * shiftTaskCount; // 긴 작업 시간

                // When: 반복 작업 실행 후 일정 횟수 실행 대기
                executor.scheduleAtFixedRate(() -> {
                    taskStartTimes.add(LocalTime.now());
                    // 첫 작업만 오래 걸리는 작업으로 모의
                    executeSparkTask(task, taskExecTime, extremeLongJobExecTime, longTaskSparked);
                }, 0, period, TimeUnit.MILLISECONDS);

                shutdownAfterSomeCycle(executor, taskStartTimes::size, shiftTaskCount);

                // Then: 작업 간 간격 검증
                assertDurationWithMargin(taskStartTimes.get(0), taskStartTimes.get(1), extremeLongJobExecTime, 100); // 첫 지연 작업
                IntStream.range(2, taskStartTimes.size()).forEach(i -> { // 이후 작업들
                    assertDurationWithMargin(taskStartTimes.get(i - 1), taskStartTimes.get(i), 100, 10);
                });
            }
        }

        /**
         * scheduleWithFixedDelay() 메서드를 테스트합니다.
         */
        @Nested
        class WithFixedDelay {
            /**
             * scheduleWithFixedDelay()로 작업 요청 시, 하나의 작업이 끝난 이후부터 delay 시간 경과 후에 다음 작업이 시작됩니다.
             */
            @Test
            void withLongTask() throws Exception {
                final long taskExecTime = 1500;
                final long delay = 1000;

                // When: 반복 작업 실행 후 일정 횟수 실행 대기
                executor.scheduleWithFixedDelay(() -> {
                    taskStartTimes.add(LocalTime.now());
                    task.accept(taskExecTime);
                }, 0, delay, TimeUnit.MILLISECONDS);

                shutdownAfterSomeCycle(executor, taskStartTimes::size, 5);

                // Then: 작업 간 간격 검증
                IntStream.range(1, taskStartTimes.size()).forEach(i ->
                        assertDurationWithMargin(taskStartTimes.get(i - 1), taskStartTimes.get(i), taskExecTime + delay, 100));
            }

            /**
             * scheduleWithFixedDelay()로 실행되는 첫 작업이 뒤 이은 N개의 작업 시작 시간 만큼 지연되더라고 뒤 이은 N개의 작업은 설정된 delay
             * 만큼 간격을 두고 실행됩니다.
             */
            @Test
            void withLongSparkTask() throws InterruptedException {
                final AtomicBoolean longTaskSparked = new AtomicBoolean(false);
                final long delay = 1000;
                final long taskExecTime = 100;
                final long longTaskExecTime = 1300;

                // When: 반복 작업 실행 후 일정 횟수 실행 대기
                executor.scheduleWithFixedDelay(() -> {
                    taskStartTimes.add(LocalTime.now());
                    // 첫 작업만 오래 걸리는 작업으로 모의
                    executeSparkTask(task, taskExecTime, longTaskExecTime, longTaskSparked);
                }, 0, delay, TimeUnit.MILLISECONDS);

                shutdownAfterSomeCycle(executor, taskStartTimes::size, 3);

                // Then: 작업 간 간격 검증
                assertDurationWithMargin(taskStartTimes.get(0), taskStartTimes.get(1), delay + longTaskExecTime, 100);
                assertDurationWithMargin(taskStartTimes.get(1), taskStartTimes.get(2), delay + taskExecTime, 100);
                assertDurationWithMargin(taskStartTimes.get(2), taskStartTimes.get(3), delay + taskExecTime, 100);
            }
        }

        /**
         * 긴 작업을 한 번 실행하고, 이후 부터는 일반적인 길이의 작업을 실행합니다.
         * @param task 작업
         * @param generalExecTime 일반적인 작업 실행 시간
         * @param longExecTime 긴 작업 실행 시간
         * @param longTaskSparked 긴 작업이 실행되었는지 여부
         */
        private void executeSparkTask(Consumer<Long> task, long generalExecTime, long longExecTime, AtomicBoolean longTaskSparked) {
            if (longTaskSparked.get()) {
                task.accept(generalExecTime);
            } else {
                task.accept(longExecTime);
                longTaskSparked.set(true);
            }
        }

        /**
         * 작업이 일정 횟수(repeatCount) 수행된 후 executor를 종료합니다.
         * @param executor executor
         * @param execCount 작업이 실행된 횟수를 반환하는 메서드참조
         * @param repeatCount 실행을 목표하는 작업 횟수
         */
        private void shutdownAfterSomeCycle(ExecutorService executor, Supplier<Integer> execCount, int repeatCount) throws InterruptedException {
            await().until(() -> execCount.get() > repeatCount);
            executor.shutdownNow();
        }
    }

    /**
     * awaitTermination() 메서드의 timeout 인자를 0을 전달하더라도 최소 1번의 termination 확인 동작을 수행합니다.
     */
    @Test
    void awaitTerminationWithZeroTimeout() throws InterruptedException {
        final long workingTime = 3000;
        final long requestTerminationTime = 500;
        final long waitTime = 4000;

        executor.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(workingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(requestTerminationTime);
        executor.shutdown();

        Thread.sleep(waitTime);
        assertTrue(executor.awaitTermination(0, TimeUnit.MILLISECONDS));
    }

    /**
     * 스케줄된 작업에서 예외가 발생하면 주기적인 작업이 중단되고, get() 메서드 호출을 통해 예외 정보를 얻을 수 있습니다.
     */
    @Test
    void throwException() throws Exception {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            log.info("실행");
            throw new IllegalArgumentException("예외 발생");
        }, 0, 100, TimeUnit.SECONDS);

        assertThrows(ExecutionException.class, () -> future.get(1000, TimeUnit.MILLISECONDS));
        assertTrue(future.isDone());
        assertTrue(!future.isCancelled());
        assertTrue(!executor.isShutdown());
        assertTrue(!executor.isTerminated());
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

        await().atMost(2000, TimeUnit.MILLISECONDS).untilAsserted(() -> {
            assertEquals("message", queue.poll());
            assertTrue(future.isDone());
            assertTrue(future.isCancelled());
            assertTrue(!executor.isShutdown());
            assertTrue(!executor.isTerminated());
        });
    }
}
