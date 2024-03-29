package com.fanhq.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class UpdSend {

    public static Integer SPORT = 1111;
    public static Integer DPORT = 6001;

    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group).channel(NioDatagramChannel.class)
                //.option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {

                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        // TODO Auto-generated method stub
                        ch.pipeline().addLast(new MyHandlerSend());
                    }
                });
        try {
            Channel channel = bootstrap.bind(SPORT).sync().channel();
            InetSocketAddress remoteAddress = new InetSocketAddress("192.168.200.48", DPORT);
            for (int i = 0; i < 5; i++) {
                TimeUnit.SECONDS.sleep(1);
                String msg = "send " + i;

                ByteBuf byteBuf1 = new UnpooledByteBufAllocator(false).buffer();
                byteBuf1.writeCharSequence(msg, Charset.forName("utf-8"));
                DatagramPacket packet = new DatagramPacket(byteBuf1, remoteAddress);
                channel.writeAndFlush(packet);
                System.out.println(msg);
            }
            channel.closeFuture().sync();
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyHandlerSend extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {

        ByteBuf buf = msg.content();
        String strMsg = buf.toString(CharsetUtil.UTF_8);
        System.out.println("Recv Send :" + strMsg);
    }
}