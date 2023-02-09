package netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Logger extends SimpleChannelInboundHandler<LogSource> {
    private final LogTarget target;

    // 수신한 메시지에 대한 로그를 남깁니다.
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogSource msg) throws Exception {
        target.log(msg.toMessage());
        ctx.fireChannelRead(msg);
    }
}
