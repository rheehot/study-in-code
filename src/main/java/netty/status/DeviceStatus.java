package netty.status;

import lombok.Getter;
import lombok.Setter;

// 장비의 전체 상태
@Getter
@Setter
public class DeviceStatus {
    int angle;
    double velocity;
    String name;
    String version;
}
