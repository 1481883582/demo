package com.udp.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by root on 1/11/18.
 */
@Slf4j
public class ChineseProverbServer {
    public void run(int port) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler((ChannelHandler) new ChineseProverbServerHandler());

            bootstrap.bind(port).sync().channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        ChineseProverbServer server = new ChineseProverbServer();
        server.run(24584);
    }

//    public void sendMessage() {
//        //多播数据套接字。类似socket
//        MulticastSocket mss = null;
//        //你要发送的消息
//        String message = "我是测试消息";
//        //组播访问地址
//        String groupUrl = "xxxxxx";
//        try {
//            mss = new MulticastSocket(8081);
//            mss.joinGroup(InetAddress.getByName(groupUrl));
//            while (true) {
//                DatagramPacket dp = new DatagramPacket(message.getBytes()
//                        , message.getBytes().length, InetAddress.getByName(groupUrl)
//                        , 8081);
//                log.info("发送时间为消息为{}，发送时间为：{}", message, LocalDateTime.now());
//                mss.send(dp);
//                //防止提交卡顿
//                TimeUnit.SECONDS.sleep(1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (mss != null) {
//                    mss.leaveGroup(InetAddress.getByName(groupUrl));
//                    mss.close();
//                }
//            } catch (Exception e2) {
//            }
//        }
//    }
}