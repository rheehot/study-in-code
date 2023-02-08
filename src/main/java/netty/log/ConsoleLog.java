package netty.log;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import netty.handler.LogTarget;

@Slf4j
public class ConsoleLog implements LogTarget {
    private final List<String> inputs = new ArrayList<>();

    @Override
    public void log(String msg) {
        log.info(msg);
        inputs.add(msg);
    }

    @Override
    public List<String> logged() {
        return inputs;
    }
}
