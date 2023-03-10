package tech.java.concurrent;

import org.junit.jupiter.api.Test;

public class InterruptThreadTest {

    // Thread.interrupt() 메서드를 사용해 쓰레드를 중단시킬 수 있습니다.
    @Test
    void interrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                // isInterrupted 메서드는 인터럽트 플래그의 상태를 확인하기만 합니다.
                // 반면에 interrupted 메서드는 인터럽트 플래그의 상태를 확인한 후, 인터럽트 플래그를 리셋합니다.
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted and current interrupt status is " + Thread.interrupted());
                    System.out.println("But Thread.interrupted() call makes interrupt status to " + Thread.currentThread().isInterrupted());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while sleeping by main thread and now interrupt status is reset to " + Thread.currentThread().isInterrupted());
                    // interrupt 메서드는 기본적으로 쓰레드의 인터럽트 플래그를 설정합니다.
                    Thread.currentThread().interrupt();
                    System.out.println("When handling to InterruptedException, re-interrupt the thread and now interrupt status is " + Thread.currentThread().isInterrupted());
                }
                Thread.yield();
            }
        });
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();
        thread.join();
    }
}
