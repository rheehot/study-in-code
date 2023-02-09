package netty.pipeline;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.netty.channel.embedded.EmbeddedChannel;
import netty.handler.Logger;
import netty.handler.StatusUpdater;
import netty.log.ConsoleLog;
import netty.message.PartialStatus;
import netty.status.TotalStatus;

public class PipelineTest {
    @Test
    @DisplayName("장비의 상태를 업데이트하고 로그를 남기는 파이프라인")
    void status_update_and_log_pipeline() {
        // Given : 상태 업데이트 및 로깅 핸드러로 파이프라인 구성
        TotalStatus totalStatus = new TotalStatus();
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline()
               .addLast(new StatusUpdater(totalStatus))
               .addLast(new Logger(new ConsoleLog()));

        // When : 부분 상태 메시지를 채널 입력으로 쓰기
        channel.writeInbound(new PartialStatus(10, 100.0));

        // Then : 파이프라인을 통과한 객체는 입력 타입과 동일
        Object throughMessage = channel.readInbound();
        Assertions.assertThat(throughMessage.getClass()).isEqualTo(PartialStatus.class);
    }
}
