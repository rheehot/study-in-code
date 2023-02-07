package netty.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import netty.handler.StatusUpdatable;
import netty.status.DeviceStatus;

// 장비 버전 정보
@Getter
@RequiredArgsConstructor
public class VersionStatus implements StatusUpdatable {
    private final String name;
    private final String version;

    @Override
    public void updateTo(DeviceStatus totalStatus) {
        totalStatus.setName(name);
        totalStatus.setVersion(version);
    }
}
