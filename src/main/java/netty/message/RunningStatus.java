package netty.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 장비 구동 상태
@Getter
@RequiredArgsConstructor
public class RunningStatus {
    private final int angle;
    private final double velocity;
}
