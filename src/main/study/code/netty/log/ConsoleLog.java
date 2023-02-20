package code.netty.log;

import code.netty.handler.LogTarget;

public class ConsoleLog implements LogTarget {
    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}
