package netty.handler;

import netty.status.DeviceStatus;

public interface StatusUpdatable {
    void updateTo(DeviceStatus totalStatus);
}
