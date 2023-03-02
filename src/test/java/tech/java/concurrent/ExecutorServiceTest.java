package tech.java.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ExecutorServiceTest {

    /**
     * 하나의 작업을 비동기적으로 실행(submit)하고 처리 완료를 기다린 뒤 결과를 확인(get)합니다.
     */
    @Test
    void runSingleTask() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            return "result";
        });

        assertEquals("result", future.get());
        assertTrue(future.isDone());
    }

    /**
     * 여러 개의 작업을 비동기적으로 실행(submit)하고 처리 완료를 기다린 뒤 결과를 확인(get)합니다.
     */
    @Test
    void runMultiTasks() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future1 = executor.submit(() -> {
            Thread.sleep(2000);
            return "result1";
        });

        Future<String> future2 = executor.submit(() -> {
            Thread.sleep(2000);
            return "result2";
        });

        assertEquals("result1", future1.get());
        assertTrue(future1.isDone());
        assertEquals("result2", future2.get());
        assertTrue(future2.isDone());
    }

    /**
     * 작업이 완료된 후 Future 객체로 취소를 시도하면 실패합니다.
     */
    @Test
    void cancelAfterCompleted() throws Exception {
        // Given : 짧은 비동기 작업 실행
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> "result");

        // When : 작업이 완료된 후 취소
        Thread.sleep(1000);
        boolean cancelled = future.cancel(false);

        // Then : 취소 실패
        assertFalse(cancelled);
        assertFalse(future.isCancelled());
        assertTrue(future.isDone());
    }

    /**
     * 인터럽트 없이 작업을 취소(cancel)하면, 작업은 끝까지 실행되지만 결과를 받을 수 없는 취소 상태가 됩니다.
     */
    @Test
    void cancelTaskWithoutInterrupt() throws Exception {
        // Given : 비동기 작업 실행
        ExecutorService executor = Executors.newSingleThreadExecutor();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            queue.put("message");
            return "result";
        });

        // When : 인터럽트 없이 작업 취소
        Thread.sleep(500); // 태스크 실행을 대략 보장
        boolean cancelResult = future.cancel(false);

        // Then : 작업은 끝까지 실행됨, 그러나 결과를 받을 수 없는 취소 상태가 됨
        assertEquals("message", queue.poll(2000, TimeUnit.MILLISECONDS));
        assertTrue(cancelResult); // 취소 성공
        assertTrue(future.isCancelled());  // 취소됨 상태
        assertTrue(future.isDone()); // 완료됨 상태
        assertThrows(CancellationException.class, future::get); // 작업 결과 역시 취소됨
    }

    /**
     * 인터럽트와 함께 작업을 취소(cancel)하면, 인터럽트 가능한 작업(Sleep과 같은)은 도중에 인터럽트 되어 종료됩니다.
     */
    @Test
    void cancelTaskWithInterrupt() throws Exception {
        // Given : 비동기 작업 실행
        ExecutorService executor = Executors.newSingleThreadExecutor();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            queue.put("message");
            return "result";
        });

        // When : 인터럽트와 함께 작업 취소
        Thread.sleep(500); // 태스크 실행을 대략 보장
        boolean cancelResult = future.cancel(true);

        // Then : 작업은 끝까지 실행되지 않음
        assertNull(queue.poll(2000, TimeUnit.MILLISECONDS));
        assertTrue(cancelResult); // 취소 성공
        assertTrue(future.isCancelled());  // 취소됨 상태
        assertTrue(future.isDone()); // 완료됨 상태
        assertThrows(CancellationException.class, future::get); // 작업 결과 역시 취소됨
    }

    /**
     * 비동기 태스크 각각의 취소(cancel)하더라도 Executor 종료 상태에 영향을 주지 않습니다.
     */
    @Test
    void cancelDoNotAffectExecutorTermination() throws Exception {
        // Given : 비동기 작업 실행
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            return "result";
        });

        // When : 인터럽트와 없이 작업 취소
        Thread.sleep(500);
        future.cancel(false);

        // Then : Executor의 종료는 관련 없음
        assertFalse(executor.awaitTermination(2000, TimeUnit.MILLISECONDS));
        assertFalse(executor.isShutdown());
        assertFalse(executor.isTerminated());
    }

    /**
     * 두 개의 비동기 작업 중 하나는 취소(cancel)하고 다른 작업은 정상적으로 완료되도록 합니다.
     */
    @Test
    void oneThreadCancelAnotherCompletes() throws Exception {
        // Given : 두개의 비동기 작업 실행
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> task = () -> {
            Thread.sleep(2000);
            return "result";
        };
        Future<String> cancelFuture = executor.submit(task);
        Future<String> completeFuture = executor.submit(task);

        // When : 첫번째 작업만 취소
        Thread.sleep(500); // 태스크에서 Sleep에 들어갈 때 까지 기다립니다.
        cancelFuture.cancel(true);

        // Then : 첫번째 작업은 취소되고, 두번째 작업은 정상적으로 완료됨
        assertThrows(CancellationException.class, cancelFuture::get);
        assertTrue(cancelFuture.isCancelled());
        assertTrue(cancelFuture.isDone());
        assertEquals("result", completeFuture.get());
        assertFalse(completeFuture.isCancelled());
        assertTrue(completeFuture.isDone());
    }

    /**
     * Executor 종료(shutdown)는 Executor에 등록된 모든 작업이 완료되면 종료됩니다.
     */
    @Test
    void shutdown() throws Exception {
        // Given : 비동기 작업 실행
        ExecutorService executor = Executors.newSingleThreadExecutor();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            queue.put("message");
            return "result";
        });

        // When : Executor 종료
        Thread.sleep(500);
        executor.shutdown();

        // Then : 태스크가 완료되면 Executor 종료
        assertEquals("result", future.get());
        assertTrue(future.isDone());
        assertEquals("message", queue.poll(2000, TimeUnit.MILLISECONDS));
        assertTrue(executor.isShutdown());
        assertTrue(executor.isTerminated());
    }

    /**
     * Executor 즉시 종료(shutdownNow)는 Executor에 등록된 모든 작업이 즉시 종료됩니다.
     */
    @Test
    void shutdownNow() throws Exception {
        // Given : 비동기 작업 실행
        ExecutorService executor = Executors.newSingleThreadExecutor();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            queue.put("message");
            return "result";
        });

        // When : Executor 즉시 종료
        Thread.sleep(500);
        executor.shutdownNow();

        // Then : 태스크가 중단되며 Executor 종료
        assertThrows(ExecutionException.class, future::get);
        assertTrue(future.isDone());
        assertNull(queue.poll(3000, TimeUnit.MILLISECONDS));
        assertTrue(executor.isShutdown());
        assertTrue(executor.isTerminated());
    }

    /**
     * Executor 종료(shutdown) 후에도 새로운 작업을 제출할 수 없습니다.
     */
    @Test
    void preventNewTaskFromSubmitting() throws Exception {
        // Given : Executor 종료
        ExecutorService executor = Executors.newSingleThreadExecutor();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            queue.put("message");
            return "result";
        });
        executor.shutdown();

        // Expected : 새로운 작업 제출 시, 작업 거부
        assertThrows(RejectedExecutionException.class,
                () -> executor.submit(() -> {
                    Thread.sleep(2000);
                    queue.put("result2");
                    return "result2";
                }));

        // Expected : 기존 작업은 정상적으로 완료
        assertEquals("result", future.get());
        assertEquals("message", queue.poll(2000, TimeUnit.MILLISECONDS));
    }
}
