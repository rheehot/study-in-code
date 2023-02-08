package netty.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import netty.handler.LogFactory;
import netty.handler.StatusUpdatable;
import netty.status.TotalStatus;

// 장비 구동 상태
@Getter
@RequiredArgsConstructor
public class PartialStatus implements StatusUpdatable, LogFactory {
    private final int angle;
    private final double velocity;

    @Override
    public void updateTo(TotalStatus totalStatus) {
        totalStatus.setAngle(angle);
        totalStatus.setVelocity(velocity);
    }

    @Override
    public String toLogMessage() {
        return String.format("angle : %d, velocity : %f", angle, velocity);
    }
}
