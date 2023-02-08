package netty.log;

import java.util.ArrayList;
import java.util.List;

import netty.handler.LogTarget;

public class ConsoleLog implements LogTarget {
    private final List<String> logged = new ArrayList<>();

    @Override
    public void log(String msg) {
        System.out.println(msg);
        logged.add(msg);
    }

    @Override
    public List<String> logged() {
        return logged;
    }
}
