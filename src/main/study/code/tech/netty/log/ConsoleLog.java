package code.tech.netty.log;

import code.tech.netty.handler.LogTarget;

public class ConsoleLog implements LogTarget {
    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}
