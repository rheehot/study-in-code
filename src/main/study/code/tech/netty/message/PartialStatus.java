package code.tech.netty.message;

import code.tech.netty.handler.LogSource;
import code.tech.netty.handler.StatusUpdatable;
import code.tech.netty.status.TotalStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 장비 구동 상태
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class PartialStatus implements StatusUpdatable, LogSource {
    private final int angle;
    private final double velocity;

    @Override
    public void updateTo(TotalStatus totalStatus) {
        totalStatus.setAngle(angle);
        totalStatus.setVelocity(velocity);
    }

    @Override
    public String toLog() {
        return String.format("angle : %d, velocity : %f", angle, velocity);
    }
}
