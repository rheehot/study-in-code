package netty.handler;

import java.util.List;

public interface LogTarget {
    void log(String msg);
    List<String> logged();
}
