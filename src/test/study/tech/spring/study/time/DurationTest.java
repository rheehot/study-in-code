package tech.spring.study.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@DisplayName("Duration")
public class DurationTest {
    @Test
    @DisplayName("toMillis() : 두 LocalDateTime의 밀리초 차이를 반환합니다.")
    void betweenToMillis() {
        LocalDateTime sample1 = LocalDateTime.of(2022, Month.AUGUST, 30, 12, 34, 56, TimeUtils.toNano(900));
        LocalDateTime sample2 = LocalDateTime.of(2022, Month.AUGUST, 30, 12, 34, 57, TimeUtils.toNano(0));
        Duration between = Duration.between(sample1, sample2);
        Assertions.assertEquals(100, between.toMillis());
    }

    @Test
    @DisplayName("getNano() 메서드는 두 LocalDateTime 객체가 가지는 시간의 차이를 계산합니다. 나노초만 분리하여 비교하는 것이 아닙니다.")
    void betweenNano() throws InterruptedException {
        final int intervalMsec = 100;
        final int marginMsec = 5;
        final int repeat = 10;
        // Given
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        CopyOnWriteArrayList<LocalDateTime> timeStampList = new CopyOnWriteArrayList<>();

        // When : N msec 간격으로 타임스탬프
        executor.scheduleAtFixedRate(() -> {
            timeStampList.add(LocalDateTime.now());
            log.info("nano timestamp = {}", LocalDateTime.now().getNano());
        }, 0, intervalMsec, TimeUnit.MILLISECONDS);

        // Then : N msec 간격 확인 (M msec 마진 허용)
        Thread.sleep(intervalMsec * repeat);
        for (int i = 1; i < timeStampList.size() - 1; i++) {
            Duration between = Duration.between(timeStampList.get(i), timeStampList.get(i + 1));
            int msecFromMinus = Math.abs((timeStampList.get(i).getNano() - timeStampList.get(i + 1).getNano())) / 1000 / 1000;
            int msecFromBetween = between.getNano() / 1000 / 1000 ;
            log.info("between msec = {}, minus msec = {}", msecFromBetween, msecFromMinus);
            Assertions.assertTrue((msecFromBetween < (intervalMsec + marginMsec)) && msecFromBetween > (intervalMsec - marginMsec));
        }
    }
}
