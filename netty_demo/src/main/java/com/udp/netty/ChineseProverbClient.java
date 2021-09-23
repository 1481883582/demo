package com.udp.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by root on 1/11/18.
 */
public class ChineseProverbClient {
    public void run(int port) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler((ChannelHandler) new ChineseProverbClientHandler());
            Channel channel = bootstrap.bind(7070).sync().channel();
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("QUERY", CharsetUtil.UTF_8), new InetSocketAddress("255.255.255.255", port))).sync();
            if (!channel.closeFuture().await(15000)) {
                System.out.println("out of time");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ChineseProverbClient().run(8080);
    }

}