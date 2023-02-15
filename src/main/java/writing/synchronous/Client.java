package writing.synchronous;

import java.util.concurrent.CompletableFuture;


public class Client {

    public long taskSync(int n, int m) {
        SumCalculator sumCalculator = new SumCalculator();
        long sumN = sumCalculator.computeSync(n);
        long sumM = sumCalculator.computeSync(m);
        return sumN + sumM;
    }

    public long taskAsync(int n, int m) {
        SumCalculator sumCalculator = new SumCalculator();
        CompletableFuture<Long> futureSumN = sumCalculator.computeAsync(n);
        CompletableFuture<Long> futureSumM = sumCalculator.computeAsync(m);
        return futureSumN.join() + futureSumM.join();
    }
}
