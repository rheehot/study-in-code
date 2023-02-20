package test.utils;

import java.util.stream.LongStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

/**
 * Intel Core i7-8565U @ 1.80GHz 환경에서 다음과 같은 결과를 확인할 수 있었습니다.
 *
 * Benchmark                  Mode  Cnt  Score   Error  Units
 * BenchMarker.sumParallel    avgt       0.748          ms/op
 * BenchMarker.sumSequential  avgt       3.044          ms/op
 */
@State(Scope.Thread)
public class BenchMarker {
    private static final int N = 10_000_000;

    @Benchmark
    public void sumParallel() {
        long result = LongStream.rangeClosed(1, N)
                                .parallel()
                                .reduce(0L, Long::sum);
    }

    @Benchmark
    public void sumSequential() {
        long result = 0;
        for (int i = 0; i < N; i++) {
            result += i;
        }
    }

    @TearDown
    public void tearDown() {
        System.gc();
    }
}
