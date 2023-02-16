package writing.synchronous;

import java.util.concurrent.CompletableFuture;
import java.util.stream.LongStream;

public class Calculator {

    public long factorial(int n) {
        // 1-N 팩토리얼 계산
        long factorial = LongStream.rangeClosed(1, n)
                                   .reduce(1, (subTotal, x) -> subTotal * x);
        // 계산 시간 모의
        simulateProcessingTime(1000);
        return factorial;
    }

    public CompletableFuture<Long> factorialAsync(int n) {
        // 별도의 쓰레드에 작업 할당
        return CompletableFuture.supplyAsync(() -> {
            return factorial(n);
        });
    }

    public long sum(int n) {
        // 1-N 합계 계산
        long sum = LongStream.rangeClosed(1, n)
                             .reduce(0, Long::sum);
        // 계산 시간 모의
        simulateProcessingTime(500);
        return sum;
    }

    public CompletableFuture<Long> sumAsync(int n) {
        // 별도의 쓰레드에 작업 할당
        return CompletableFuture.supplyAsync(() ->{
            return sum(n);
        });
    }

    public void simulateProcessingTime(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
