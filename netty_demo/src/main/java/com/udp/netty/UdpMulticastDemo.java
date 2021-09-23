package com.udp.netty;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Udp 组播方式示范
 */
public class UdpMulticastDemo {
    public static void main(String[] args) {
        new Thread(()->{
            UdpMulticastServer server = null;
            try {
                server = new UdpMulticastServer("232.14.12.16",24584);
                System.out.println("服务器启动成功,接收数据...");
                while (true){
                    byte[] data = server.receive(1024);
                    System.out.println("服务器收到数据：");
                    System.out.println(new String(data));
                    Thread.yield();
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (server != null){
                    server.close();
                }
            }
        }).start();

//        new Thread(()->{
//            UdpMulticastClient client = null;
//            try {
//                Thread.sleep(1500);
//                client = new UdpMulticastClient("224.0.1.0",5000);
//                while (true){
//                    System.out.println("客户端启动成功");
//                    client.send("你好，我是客户端");
//                    System.out.println("客户成功发送数据");
//                    Thread.yield();
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                if (client != null){
//                    client.close();
//                }
//            }
//        }).start();
    }
}
class UdpMulticastServer {
    private MulticastSocket multicastSocket;
    private InetAddress inetAddress;
    private int port;
    public UdpMulticastServer(String address, int port) throws IOException {
        inetAddress = InetAddress.getByName(address);
        this.port = port;
        multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(this.inetAddress);
    }
    public byte[] receive(int length) throws IOException {
        DatagramPacket packet = new DatagramPacket(new byte[length],length);
        this.multicastSocket.receive(packet);
        return packet.getData();
    }
    public void close(){
        if (this.multicastSocket != null && !this.multicastSocket.isClosed()){
            this.multicastSocket.close();
        }
    }
}
class UdpMulticastClient {
    private DatagramSocket socket;
    private InetAddress inetAddress;
    private int port;
    public UdpMulticastClient(String address, int port) throws IOException {
        inetAddress = InetAddress.getByName(address);
        this.port = port;
        socket = new MulticastSocket();
    }
    public void send(String msg) throws IOException {
        byte[] data = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(data,data.length,this.inetAddress,port);
        this.socket.send(packet);
    }
    public void close(){
        if (this.socket != null && !this.socket.isClosed()){
            this.socket.close();
        }
    }
}


