package netty.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import netty.handler.StatusUpdatable;
import netty.status.DeviceStatus;

// 장비 구동 상태
@Getter
@RequiredArgsConstructor
public class RunningStatus implements StatusUpdatable {
    private final int angle;
    private final double velocity;

    @Override
    public void updateTo(DeviceStatus totalStatus) {
        totalStatus.setAngle(angle);
        totalStatus.setVelocity(velocity);
    }
}
