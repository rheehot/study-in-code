package code.netty.handler;

import code.netty.status.TotalStatus;

public interface StatusUpdatable {
    void updateTo(TotalStatus totalStatus);
}
