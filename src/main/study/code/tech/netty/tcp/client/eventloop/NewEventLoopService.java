package code.tech.netty.tcp.client.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TCP 연결 실패 시, 연결 시도를 위해 할당된 EventLoop(I/O 쓰레드) 누수가 발생하는 코드와 안전하게 자원을 해제하는
 * 코드를 비교 분석하기 위한 서비스를 제공합니다.
 */
@Service
@Slf4j
public class NewEventLoopService {
    private final EventLoopGroup reusedEventLoopGroup = new NioEventLoopGroup();
    private final Bootstrap bootstrap = new Bootstrap();
    private Channel channel;

    /**
     * EventLoopGroup을 재사용하여 연결을 시도합니다. 연결에 실패한 경우, 연결 시도에 사용된 EventLoop를
     * 해제하지 않아 쓰레드 누수가 발생하게 됩니다.
     */
    public void leakIfFailed(String ip, int port) throws InterruptedException {
        channel = bootstrap.group(reusedEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder());
                    }
                }).connect(ip, port).sync().channel();
    }

    /**
     * EventLoopGroup을 재사용하여 연결을 시도합니다. 연결에 실패한 경우, 연결 시도에 사용된 EventLoop를
     * 해제함으로 쓰레드 누수가 발생하지 않습니다.
     */
    public void releaseIfFailed(String ip, int port) throws InterruptedException {
        channel = bootstrap.group(reusedEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder());
                    }
                }).connect(ip, port).addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        future.channel().eventLoop().shutdownGracefully();
                    }
                }).sync().channel();
    }

    /**
     * EventLoopGroup을 재사용함으로 채널에 할당된 EventLoop 쓰레드만 해제합니다.
     */
    public void disconnect(){
        if (channel != null) {
            channel.close();
        }
    }
}
