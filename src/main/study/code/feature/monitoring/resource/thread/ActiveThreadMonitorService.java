package code.feature.monitoring.resource.thread;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ActiveThreadMonitorService {
    private final Map<String, Set<Thread>> activeThreadsTimeline = new HashMap<>();

    // 현재 Active 쓰레드 캡처
    public void captureActiveThreads(String timeTag) {
        Set<Thread> activeThreads = getActiveThreads();
        activeThreadsTimeline.put(timeTag, activeThreads);
    }

    // 이전 조회 결과와 비교해 새롭게 생성된 Active 쓰레드만 조회
    public List<String> getDiffActiveThreads(String beforeTimeTag) {
        Set<Thread> beforeActiveThreads = activeThreadsTimeline.get(beforeTimeTag);
        Set<Thread> currentActiveThreads = getActiveThreads();

        return currentActiveThreads.stream()
                                   .filter(Thread::isAlive)
                                   .filter(thread -> !beforeActiveThreads.contains(thread))
                                   .map(Thread::getName)
                                   .sorted()
                                   .collect(Collectors.toList());
    }

    // 이전 조회 결과와 비교해 새롭게 생성된 Active 쓰레드만 조회
    public List<String> getAllActiveThreads() {
        Set<Thread> currentActiveThreads = getActiveThreads();

        return currentActiveThreads.stream()
                                   .filter(Thread::isAlive)
                                   .map(Thread::getName)
                                   .sorted()
                                   .collect(Collectors.toList());
    }

    // 현재 Active 쓰레드 조회
    private Set<Thread> getActiveThreads() {
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        return threads.stream()
                .filter(Thread::isAlive)
                .collect(Collectors.toSet());
    }
}
