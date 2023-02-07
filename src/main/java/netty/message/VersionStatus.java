package netty.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import netty.handler.LogFactory;
import netty.handler.StatusUpdatable;
import netty.status.DeviceStatus;

// 장비 버전 정보
@Getter
@RequiredArgsConstructor
public class VersionStatus implements StatusUpdatable, LogFactory {
    private final String name;
    private final String version;

    @Override
    public void updateTo(DeviceStatus totalStatus) {
        totalStatus.setName(name);
        totalStatus.setVersion(version);
    }

    @Override
    public String toLogMessage() {
        return String.format("name : %s, version : %s", name, version);
    }
}
