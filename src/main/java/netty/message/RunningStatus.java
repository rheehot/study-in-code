package netty.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import netty.status.DeviceStatus;

// 장비 구동 상태
@Getter
@RequiredArgsConstructor
public class RunningStatus {
    private final int angle;
    private final double velocity;

    public void updateTo(DeviceStatus totalStatus) {
        totalStatus.setAngle(angle);
        totalStatus.setVelocity(velocity);
    }
}
