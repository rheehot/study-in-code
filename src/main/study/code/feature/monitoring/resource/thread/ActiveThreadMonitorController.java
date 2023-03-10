package code.feature.monitoring.resource.thread;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// 현재 Active 쓰레드를 캡처하고, 이전 조회 결과와 비교해 새롭게 생성된 Active 쓰레드만 조회하는 기능을 제공합니다.
@RestController
@RequestMapping(path = "/resource/thread/")
@AllArgsConstructor
public class ActiveThreadMonitorController {
    private final ActiveThreadMonitorService activeThreadMonitorService;

    // 현재 Active 쓰레드 캡처
    @PutMapping(path = "/active/capture/{timeTag}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> captureActiveThreads(@PathVariable String timeTag) {
        activeThreadMonitorService.captureActiveThreads(timeTag);
        return ResponseEntity.ok().build();
    }

    // N 초 동안 실행되는 시뮬레이션 쓰레드 생성 후 실행
    @PutMapping(path = "/new/{runningTimeMillis}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> captureActiveThreads(@PathVariable int runningTimeMillis) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(runningTimeMillis);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        });
        return ResponseEntity.ok().build();
    }

    // 이전 조회 결과와 비교해 새롭게 생성된 Active 쓰레드만 조회
    @GetMapping(path = "/active/diff/{beforeTimeTag}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDiffActiveThreads(@PathVariable String beforeTimeTag) {
        return ResponseEntity.ok(activeThreadMonitorService.getDiffActiveThreads(beforeTimeTag));
    }

    // 현재 Active 쓰레드 조회
    @GetMapping(path = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getActiveThreadsAll() {
        return ResponseEntity.ok(activeThreadMonitorService.getAllActiveThreads());
    }
}
