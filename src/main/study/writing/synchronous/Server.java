package writing.synchronous;

import java.util.concurrent.CompletableFuture;

public class Server {
    private final Calculator calculator = new Calculator();

    public long work(int n, int m) {
        long factorial = calculator.factorial(n);
        long sum = calculator.sum(m);
        return factorial + sum;
    }

    public long workAsync(int n, int m) {
        CompletableFuture<Long> factorialFuture = calculator.factorialAsync(n);
        CompletableFuture<Long> sumFuture = calculator.sumAsync(m);
        return factorialFuture.join() + sumFuture.join();
    }
}
