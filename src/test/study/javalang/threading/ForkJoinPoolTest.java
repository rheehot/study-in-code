package javalang.threading;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ForkJoinPoolTest {

    /**
     * ForkJoinPool 은 RecursiveTask 를 이용하여 작업을 분할하고 병렬로 처리할 수 있습니다.
     * ForkJoinPool 은 기본적으로 런타임의 프로세서 개수와 동일한 수의 쓰레드를 갖습니다.
     */
    @Test
    public void forkJoinSum() {
        // given
        long[] numbers = LongStream.rangeClosed(0, 10_000_000).toArray();
        ForkJoinTask<Long> task = new ForJoinSumCalculator(numbers);

        // when
        Long result = new ForkJoinPool().invoke(task);

        // then
        Assertions.assertEquals(50_000_005_000_000L, result);
    }

    static class ForJoinSumCalculator extends RecursiveTask<Long> {
        private final long[] numbers;
        private final int start;
        private final int end;
        private static final long THRESHOLD = 10_000;

        ForJoinSumCalculator(long[] numbers) {
            this(numbers, 0, numbers.length - 1);
        }

        ForJoinSumCalculator(long[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start + 1;
            if (length < THRESHOLD) {
                return computeSequentially();
            }

            int mid = start + (length / 2);

            ForJoinSumCalculator leftTask = new ForJoinSumCalculator(numbers, start, mid);
            leftTask.fork();

            ForJoinSumCalculator rightTask = new ForJoinSumCalculator(numbers, mid + 1, end);
            Long rightSum = rightTask.compute();

            Long leftSum = leftTask.join();
            return rightSum + leftSum;
        }

        private Long computeSequentially() {
            long sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
    }
}

