package com.leetCode.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Hachel on 2018/10/25
 */
public class NettyClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new NettyClientHandler());
                }

            });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8088).sync();
            Channel channel = channelFuture.channel();
            if (channel != null && channel.isOpen()) {
                channel.writeAndFlush("hello world").sync();
            } else {
                throw new Exception("channel is null | closed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();

        }
    }

    public static class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

        private ByteBuf content;
        private ChannelHandlerContext ctx;

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            this.ctx = ctx;

            // Initialize the message.
            content = ctx.alloc().directBuffer(256).writeZero(256);

            // Send the initial messages.
            generateTraffic();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            content.release();
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            // Server is supposed to send nothing, but if it sends something, discard it.
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // Close the connection when an exception is raised.
            cause.printStackTrace();
            ctx.close();
        }

        long counter;

        private void generateTraffic() {
            // Flush the outbound buffer to the socket.
            // Once flushed, generate the same amount of traffic again.
            ctx.writeAndFlush(content.retainedDuplicate()).addListener(trafficGenerator);
        }

        private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    generateTraffic();
                } else {
                    future.cause().printStackTrace();
                    future.channel().close();
                }
            }
        };

    }
}
