package writing.synchronous;

import java.util.concurrent.CompletableFuture;
import java.util.stream.LongStream;

public class SumCalculator {

    public long computeSync(int n) {
        return LongStream.rangeClosed(1, n).sum();
    }

    public CompletableFuture<Long> computeAsync(int n) {
        return CompletableFuture.supplyAsync(() -> LongStream.rangeClosed(1, n).sum());
    }
}
