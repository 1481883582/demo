package com.udp.netty_demo;

import com.udp.netty.UdpGroupServer;
import com.udp.netty.UpdGroupClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class, args);
        UdpGroupServer server = new UdpGroupServer();
        server.start();
    }

}
