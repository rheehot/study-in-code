package code.netty.tcp.client.connect.eventloop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/tcp/client/connect/event-loop-group")
@RequiredArgsConstructor
public class NewEventLoopGroupController {
    private final NewEventLoopGroupService newEventLoopGroupService;

    @PutMapping(path = "/release/{ip}/{port}")
    public ResponseEntity<Object> releaseIfFailed(@PathVariable String ip, @PathVariable int port) throws InterruptedException {
        newEventLoopGroupService.releaseIfFailed(ip, port);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/leak/{ip}/{port}")
    public ResponseEntity<Object> leakIfFailed(@PathVariable String ip, @PathVariable int port) throws InterruptedException {
        newEventLoopGroupService.leakIfFailed(ip, port);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(path = "/disconnect")
    public ResponseEntity<Object> disconnect() throws InterruptedException {
        newEventLoopGroupService.disconnect();
        return ResponseEntity.ok().build();
    }
}
