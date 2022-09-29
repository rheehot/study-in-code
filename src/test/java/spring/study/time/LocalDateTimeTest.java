package spring.study.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.concurrent.*;

@Slf4j
public class LocalDateTimeTest {
    @Test
    void isBefore() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusNanos(1000);
        Assertions.assertTrue(now.isBefore(future));
    }

    @Test
    void isAfter() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusNanos(1000);
        Assertions.assertTrue(now.isAfter(past));
    }

    @Test
    void nanoSecondTest() throws InterruptedException {
        final int intervalMsec = 100;
        final int marginMsec = 5;
        // Given
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        CopyOnWriteArrayList<LocalDateTime> timeStampList = new CopyOnWriteArrayList<>();

        // When : N msec 간격으로 타임스탬프
        executor.scheduleAtFixedRate(() -> {
            timeStampList.add(LocalDateTime.now());
        }, 0, intervalMsec, TimeUnit.MILLISECONDS);

        // Then : N msec 간격 확인 (M msec 마진 허용)
        Thread.sleep(intervalMsec * 10);
        for (int i = 0; i < timeStampList.size() - 1; i++) {
            Duration between = Duration.between(timeStampList.get(i), timeStampList.get(i + 1));
            int betweenMsec = between.getNano() / 1000 / 1000 ;
            Assertions.assertTrue((betweenMsec < (intervalMsec + marginMsec)) && betweenMsec > (intervalMsec - marginMsec));
        }
    }

}
