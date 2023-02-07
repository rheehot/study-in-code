package netty.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.netty.channel.embedded.EmbeddedChannel;
import netty.message.RunningStatus;
import netty.message.VersionStatus;
import netty.status.DeviceStatus;

// 테스트 코드
public class StatusUpdaterTest {
    StatusUpdater statusUpdater; // 테스트 대상 핸들러
    EmbeddedChannel channel; // 핸들러 테스트를 위한 채널
    DeviceStatus totalStatus; // 장비 전체 상태

    // 테스트 픽스처
    @BeforeEach
    void beforeEach() {
        statusUpdater = new StatusUpdater(totalStatus);
        totalStatus = new DeviceStatus();
        channel = new EmbeddedChannel();

        channel.pipeline().addLast(statusUpdater); // 채널 파이프라인 구성
    }

    @Test
    @DisplayName("장비의 부분 상태 메시지가 전체 상태 객체에 반영됩니다.")
    void piece_of_status_reflects_to_total_status() {
        // Given : 부분 상태 메시지 준비
        RunningStatus runningStatus = new RunningStatus(10, 100.0);
        VersionStatus versionStatus = new VersionStatus("my-device", "0.1");

        // When : 부분 상태 메시지를 채널 입력으로 쓰기
        channel.writeInbound(runningStatus);
        channel.writeInbound(versionStatus);

        // Then : 부분 상태 메시지가 전체 상태 객체에 반영
        Assertions.assertThat(10).isEqualTo(totalStatus.getAngle());
        Assertions.assertThat(100.0).isEqualTo(totalStatus.getVelocity());
        Assertions.assertThat("my-device").isEqualTo(totalStatus.getName());
        Assertions.assertThat("0.1").isEqualTo(totalStatus.getVersion());
    }
}
