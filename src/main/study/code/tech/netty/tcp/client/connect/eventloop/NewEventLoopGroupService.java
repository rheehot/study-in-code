package code.tech.netty.tcp.client.connect.eventloop;

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
 * Netty 프레임워크를 활용한 TCP 연결 과정에서 I/O 입출력을 담당하는 쓰레드 (EventLoop)의 할당과 해제 원리를 이해합니다.
 */
@SuppressWarnings("DuplicatedCode")
@Service
@Slf4j
public class NewEventLoopGroupService {
    private Channel channel;

    public void leakIfFailed(String ip, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        channel = connect(ip, port, bootstrap, eventLoopGroup).sync().channel();
        log.info("Connection success in leakIfFailed context : " + ip + ':' + port);
    }

    public void releaseIfFailed(String ip, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        channel = connect(ip, port, bootstrap, eventLoopGroup)
                .addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        eventLoopGroup.shutdownGracefully();
                    }
                }).sync().channel();
        log.info("Connection success in releaseIfFailed context : " + ip + ':' + port);
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


    public void disconnect(){
        if (channel != null) {
            channel.eventLoop().parent().shutdownGracefully();
        }
    }
}
