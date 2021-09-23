package com.udp.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * Created by root on 1/11/18.
 */
public class ChineseProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String result = packet.content().toString(CharsetUtil.UTF_8);
        System.out.println("client>>>" + result);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {

    }
}