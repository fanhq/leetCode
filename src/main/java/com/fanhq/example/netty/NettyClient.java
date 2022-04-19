package com.fanhq.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/10/25
 */
public class NettyClient {

    public static void main(String[] args) {
        // 客户端只需要一个EventLoopGroup
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            //pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            // 最后添加我们自己的处理器
                            pipeline.addLast(new MyClientHandler());
                        }
                    }
            );

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
           // ByteBuf content = Unpooled.copiedBuffer("i am client", CharsetUtil.UTF_8);
            // 发送客户端的请求
            Channel channel = channelFuture.channel();
            channel.writeAndFlush("i am client");

            TimeUnit.SECONDS.sleep(10);
            channel.writeAndFlush("i am client");

            TimeUnit.SECONDS.sleep(10);
            ChannelFuture channelFuture1 = channel.writeAndFlush("i am client");
            System.out.println(channelFuture.isSuccess());

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static class MyClientHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            //System.out.println(ctx.channel().remoteAddress());
            System.out.println("server response: " + msg);
            //ctx.writeAndFlush("from clinet: " + LocalDateTime.now());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

    }
}
