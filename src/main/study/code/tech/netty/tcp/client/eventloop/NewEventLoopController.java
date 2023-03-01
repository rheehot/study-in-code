package code.tech.netty.tcp.client.eventloop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/tcp/client/event-loop")
@RequiredArgsConstructor
public class NewEventLoopController {
    private final NewEventLoopService newEventLoopService;

    @PutMapping(path = "/connect/release/{ip}/{port}")
    public ResponseEntity<Object> releaseIfFailed(@PathVariable String ip, @PathVariable int port) throws InterruptedException {
        newEventLoopService.releaseIfFailed(ip, port);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/connect/leak/{ip}/{port}")
    public ResponseEntity<Object> leakIfFailed(@PathVariable String ip, @PathVariable int port) throws InterruptedException {
        newEventLoopService.leakIfFailed(ip, port);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/disconnect")
    public ResponseEntity<Object> disconnect() throws InterruptedException {
        newEventLoopService.disconnect();
        return ResponseEntity.ok().build();
    }
}
