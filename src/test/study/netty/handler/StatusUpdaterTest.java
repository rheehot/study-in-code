package netty.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.netty.channel.embedded.EmbeddedChannel;
import netty.message.PartialStatus;
import netty.status.TotalStatus;

@DisplayName("상태 업데이트 핸들러 테스트")
public class StatusUpdaterTest {
    @Test
    @DisplayName("장비의 부분 상태 메시지가 전체 상태 객체에 반영됩니다.")
    void partial_status_reflects_to_total_status() {
        // Given : 테스트 준비
        TotalStatus totalStatus = new TotalStatus(); // 장비 전체 상태
        StatusUpdater statusUpdater = new StatusUpdater(totalStatus); // 테스트 대상 핸들러
        EmbeddedChannel channel = new EmbeddedChannel(); // 핸들러 테스트를 위한 채널
        channel.pipeline().addLast(statusUpdater); // 채널 파이프라인 구성

        // When : 부분 상태 메시지를 채널 입력으로 쓰기
        PartialStatus partialStatus = new PartialStatus(10, 100.0);
        channel.writeInbound(partialStatus);

        // Then : 부분 상태 메시지가 전체 상태 객체에 반영
        Assertions.assertThat(10).isEqualTo(totalStatus.getAngle());
        Assertions.assertThat(100.0).isEqualTo(totalStatus.getVelocity());
    }
}
