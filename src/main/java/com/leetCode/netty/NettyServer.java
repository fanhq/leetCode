package com.leetCode.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by Hachel on 2018/3/8
 */
public class NettyServer {

    public static void main(String args[]) throws Exception {
        // 分发任务
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 实际处理的
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 启动服务
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8))
                                    .addLast(new StringEncoder(CharsetUtil.UTF_8))
                                    .addLast("testServerOutHandler1", new TestServerOutHandler())
                                    .addLast("testServerOutHandler2", new TestServerOutHandler())
                                    .addLast("testServerInHandler", new TestServerInHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static class TestServerInHandler extends SimpleChannelInboundHandler<Object> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(ctx.name());
            System.out.println("client request: " + msg);
            ByteBuf content = Unpooled.copiedBuffer("i am server", CharsetUtil.UTF_8);
            ctx.writeAndFlush(content);
        }
    }

    public static class TestServerOutHandler extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
            System.out.println(ctx.name());
        }
    }

}
