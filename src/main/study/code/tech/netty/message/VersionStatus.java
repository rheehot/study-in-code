package code.tech.netty.message;

import code.tech.netty.handler.LogSource;
import code.tech.netty.handler.StatusUpdatable;
import code.tech.netty.status.TotalStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    public String toLog() {
        return String.format("name : %s, version : %s", name, version);
    }
}
