package tech.java.concurrent;

import org.junit.jupiter.api.Test;

public class InterruptThreadTest {

    // Thread.interrupt() 메서드를 사용해 쓰레드를 중단시킬 수 있습니다.
    @Test
    void interrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted!");
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while sleeping");
                    // set the interrupted status
                    Thread.currentThread().interrupt();
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
