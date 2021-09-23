package com.udp.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

/**
 * Created by root on 1/11/18.
 */
public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static String[] DIRC = {"哈哈哈哈", "呵呵呵", "嘻嘻嘻"};

    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String req = packet.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
        if (req.equalsIgnoreCase("QUERY")) {
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("RESULT:" + nextQuote(), CharsetUtil.UTF_8), packet.sender()));


        }else {
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("RESULT:" + "ERR", CharsetUtil.UTF_8), packet.sender()));
        }
    }

    private String nextQuote() {
        int quote = ThreadLocalRandom.current().nextInt(DIRC.length);
        return DIRC[quote];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {

    }
}