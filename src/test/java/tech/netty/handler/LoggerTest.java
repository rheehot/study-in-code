package tech.netty.handler;

import code.tech.netty.handler.LogTarget;
import code.tech.netty.handler.Logger;
import code.tech.netty.log.ConsoleLog;
import code.tech.netty.message.User;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoggerTest {
    @Test
    void log_received_message() {
        // Given : 채널 파이프라인 구성
        LogTarget logTarget = Mockito.mock(ConsoleLog.class); // ConsoleLog 목킹
        Logger logger = new Logger(logTarget); // 테스트 대상 핸들러
        EmbeddedChannel channel = new EmbeddedChannel(); // 핸들러 테스트 위한 채널
        channel.pipeline().addLast(logger); // 채널 파이프라인 구성

        // When : 로깅할 메시지 입력
        User user = new User("Joo", 40);
        channel.writeInbound(user);

        // Then : 로깅 확인
        String expected = user.toLog();
        Mockito.verify(logTarget, Mockito.times(1)).log(expected);
    }
}
