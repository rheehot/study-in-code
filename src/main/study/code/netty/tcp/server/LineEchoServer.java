package code.netty.tcp.server;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LineEchoServer {
    private final EventLoopGroup acceptEventLoopGroup = new NioEventLoopGroup();
    private final EventLoopGroup serviceEventLoopGroup = new NioEventLoopGroup();
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private static final int PORT = 12345;

    @PostConstruct
    public void start() {
        bootstrap.group(acceptEventLoopGroup, serviceEventLoopGroup)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LineBasedFrameDecoder(1024))
                                .addLast(new StringDecoder())
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    // Echo Handler
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("Server received: " + msg);
                                        ctx.channel().writeAndFlush("Response -" + msg);
                                    }
                                })
                                .addLast(new StringEncoder());
                    }
                }).bind(PORT)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("Server started at port: {}", PORT);
                    } else {
                        log.error("Server failed to start at port: {}", PORT);
                    }
                });
    }
}
