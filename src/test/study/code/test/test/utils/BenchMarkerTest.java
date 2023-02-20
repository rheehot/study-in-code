package code.test.test.utils;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import code.test.utils.BenchMarker;

public class BenchMarkerTest {

    @Test
    void benchMarker() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchMarker.class.getSimpleName())
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .jvmArgs("-Xms4G", "-Xmx4G")
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(2))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(2))
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
