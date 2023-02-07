package netty.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 장비 버전 정보
@Getter
@RequiredArgsConstructor
public class VersionStatus {
    private final String name;
    private final String version;
}
