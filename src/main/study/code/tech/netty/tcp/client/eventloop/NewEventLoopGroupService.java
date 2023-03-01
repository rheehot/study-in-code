package code.tech.netty.tcp.client.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TCP 연결 실패 시, 연결 시도를 위해 할당된 EventLoopGroup 전체 쓰레드 누수가 발생하는 코드와 안전하게 자원을
 * 해제하는 코드를 비교 분석하기 위한 서비스를 제공합니다.
 */
@Service
@Slf4j
public class NewEventLoopGroupService {
    private Channel channel;

    /**
     * EventLoopGroup을 연결 시도 시 마다 새롭게 생성합니다. 연결에 실패한 경우, 연결 시도에 사용된
     * EventLoopGroup을 해제하지 않아 쓰레드 누수가 발생하게 됩니다.
     */
    public void leakIfFailed(String ip, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        channel = connect(ip, port, bootstrap, eventLoopGroup).sync().channel();
    }

    /**
     * EventLoopGroup을 연결 시도 시 마다 새롭게 생성합니다. 연결에 실패한 경우, 연결 시도에 사용된
     * EventLoopGroup을 해제함으로 쓰레드 누수가 발생하지 않습니다.
     */
    public void releaseIfFailed(String ip, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        channel = connect(ip, port, bootstrap, eventLoopGroup)
                .addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        eventLoopGroup.shutdownGracefully();
                    }
                }).sync().channel();
    }

    private ChannelFuture connect(String ip, int port, Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {
        return bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(ip, port)
                .handler(
                        new ChannelInitializer<>() {
                            @Override
                            protected void initChannel(Channel ch) throws Exception {
                                ch.pipeline()
                                        .addLast(new LoggingHandler(LogLevel.INFO))
                                        .addLast(new StringDecoder())
                                        .addLast(new StringEncoder());
                            }
                        })
                .connect();
    }

    /**
     * 채널 별로 EventLoopGroup이 연관됨으로 EventLoopGroup 전체를 해제합니다.
     */
    public void disconnect(){
        if (channel != null) {
            channel.close();
        }
    }
}
