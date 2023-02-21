package code.tech.netty.tcp.client.connect.eventloop;

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

import javax.annotation.PostConstruct;

@SuppressWarnings("DuplicatedCode")
@Service
@Slf4j
public class NewEventLoopService {
    private final EventLoopGroup reusedEventLoopGroup = new NioEventLoopGroup();
    private final Bootstrap bootstrap = new Bootstrap();
    private Channel channel;

    @PostConstruct
    public void initialize() {
        bootstrap.group(reusedEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder());
                    }
                });
    }

    /**
     * 반복전인 연결에 EventLoopGroup을 재사용하는 경우, EventLoopGroup 내부에서 EventLoop 쓰레드가 추가로 할당됩니다. 그러면 연결에
     * 실패하는 경우 더 이상 사용하지 않는 EventLoop 쓰레드가 해제되지 않고 남아있게 됩니다.
     */
    public void leakIfFailed(String ip, int port) throws InterruptedException {
        channel = bootstrap.connect(ip, port).sync().channel();
        log.info("connectReused() success : " + ip + ':' + port);
    }

    /**
     * 반복적인 연결에 EventLoopGroup을 재사용하지만, 연결 실패 시 사용했던 EventLoop를 해제해 줍니다.
     */
    public void releaseIfFailed(String ip, int port) throws InterruptedException {
        channel = bootstrap.connect(ip, port).addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                future.channel().eventLoop().shutdownGracefully();
            }
        }).sync().channel();
    }

    public void disconnect(){
        if (channel != null) {
            channel.eventLoop().shutdownGracefully();
        }
    }
}
