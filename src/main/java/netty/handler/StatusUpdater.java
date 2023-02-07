package netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import netty.status.DeviceStatus;

// Netty InboundHandler
@RequiredArgsConstructor
public class StatusUpdater extends SimpleChannelInboundHandler<StatusUpdatable> {
    private final DeviceStatus totalStatus; // 장비의 전체 상태

    // 장비의 부분적인 상태 메시지를 받아 전체 상태의 일부를 업데이트 합니다.
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StatusUpdatable msg) throws Exception {
        msg.updateTo(totalStatus);
    }
}
