package com.fanhq.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author: fanhaiqiu
 * @date: 2022/4/18
 */
public class UdpClient  {

    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                    }
                });
        ChannelFuture channelFuture = bootstrap.bind(6002).sync();

        InetSocketAddress remoteAddress = new InetSocketAddress("192.168.0.103", 6001);
        ByteBuf byteBuf1 = new UnpooledByteBufAllocator(false).buffer();
        byteBuf1.writeCharSequence("hello", Charset.forName("utf-8"));
        DatagramPacket packet = new DatagramPacket(byteBuf1, remoteAddress);
        channelFuture.channel().writeAndFlush(packet);

        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
