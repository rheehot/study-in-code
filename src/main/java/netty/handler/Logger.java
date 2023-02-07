package netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Logger extends SimpleChannelInboundHandler<LogFactory> {

    // 수신한 메시지에 대한 로그를 남깁니다.
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogFactory msg) throws Exception {
        log.info("Received content : {}", msg.toLogMessage());
        ctx.fireChannelRead(msg);
    }
}
