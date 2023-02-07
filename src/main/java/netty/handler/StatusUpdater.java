package netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import netty.message.RunningStatus;
import netty.message.VersionStatus;
import netty.status.DeviceStatus;

// Netty InboundHandler
@RequiredArgsConstructor
public class StatusUpdater extends ChannelInboundHandlerAdapter {
    private final DeviceStatus deviceStatus; // 장비의 전체 상태

    // 장비의 부분적인 상태 메시지를 받아 전체 상태의 일부를 업데이트 합니다.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RunningStatus runningStatus) { // 장비 구동 상태
            runningStatus.updateTo(deviceStatus);
        } else if (msg instanceof VersionStatus versionStatus) { // 장비 버전 정보
            versionStatus.updateTo(deviceStatus);
        } else {
            assert false;
        }
        super.channelRead(ctx, msg);
    }
}
