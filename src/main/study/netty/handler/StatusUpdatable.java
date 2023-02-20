package netty.handler;

import netty.status.TotalStatus;

public interface StatusUpdatable {
    void updateTo(TotalStatus totalStatus);
}
