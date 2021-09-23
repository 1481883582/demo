package com.udp.netty;


import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.UUID;

public class UdpGroupServer {
    private String id = UUID.randomUUID().toString();
    public boolean closed = false;
    public String ip = "232.14.12.16";//组播虚拟地址
    public int port = 24584;//组播Ip
    public int MessageIndex = 0;

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("UdpTestServer start ");
                runServer();
            }
        }).start();
    }

    private void runServer(){
        try {
            InetAddress group = InetAddress.getByName(ip);
            MulticastSocket s = new MulticastSocket(port);
            byte[] arb = new byte[1024];
            s.joinGroup(group);//加入该组
            while(!closed){
                DatagramPacket datagramPacket =new DatagramPacket(arb, arb.length);
                s.receive(datagramPacket);
                System.out.println(id + ":received packet from " + datagramPacket.getAddress().getHostAddress() + " : " + datagramPacket.getPort());
//                System.out.println(new String(arb));
                Thread.sleep(2000);
                send();
                Thread.yield();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UdpTestServer run Exception: "+e.toString());
        }
    }




    public void send(){
        try{
//            String sendMessage="hello ,message from server,"+MessageIndex++;
            String sendMessage = String.valueOf(MessageIndex++);
            byte[] message = sendMessage.getBytes(); //发送信息
            InetAddress inetAddress = InetAddress.getByName(ip); //指定组播地址
            DatagramPacket datagramPacket = new DatagramPacket(message, message.length, inetAddress, port); //发送数据包囊
            MulticastSocket multicastSocket = new MulticastSocket();//创建组播socket
            multicastSocket.send(datagramPacket);
        }catch (Exception e) {
            System.out.println("UdpTestServer send Exception: "+e.toString());
        }

        if(MessageIndex>=50){
            closed = true;
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        UdpGroupServer server = new UdpGroupServer();
        server.start();
    }

}
