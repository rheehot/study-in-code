package netty.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import netty.handler.LogSource;
import netty.handler.StatusUpdatable;
import netty.status.TotalStatus;

// 장비 버전 정보
@Getter
@RequiredArgsConstructor
public class VersionStatus implements StatusUpdatable, LogSource {
    private final String name;
    private final String version;

    @Override
    public void updateTo(TotalStatus totalStatus) {
        totalStatus.setName(name);
        totalStatus.setVersion(version);
    }

    @Override
    public String toMessage() {
        return String.format("name : %s, version : %s", name, version);
    }
}
