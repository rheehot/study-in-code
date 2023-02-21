package code.tech.netty.handler;

import code.tech.netty.status.TotalStatus;

public interface StatusUpdatable {
    void updateTo(TotalStatus totalStatus);
}
