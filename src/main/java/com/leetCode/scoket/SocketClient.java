package com.leetCode.scoket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Hachel on 2018/1/24
 */
public class SocketClient {

    public static void main(String[] args) {
        String msgPackageFmt = "1|%s|云南移动欢迎您!";
        long phoneNum = 15997100006l;
        String msgPackage = String.format(msgPackageFmt, phoneNum) ;
        sentMsg(msgPackage);
//        for (int i = 1; i <2; i ++){
//            String msgPackage = String.format(msgPackageFmt, phoneNum + i) ;
//            sentMsg(msgPackage);
//        }
    }

    /**
     * 发送短信
     * @param msgPackage
     */
    private static void sentMsg(String msgPackage){
        DataOutputStream doc;
        DataInputStream in;
        Socket socket = null;
        try {
            socket = new Socket();
            SocketAddress address = new InetSocketAddress("10.1.241.103", 8298);
            //SocketAddress address = new InetSocketAddress("127.0.0.1", 8298);
            socket.connect(address, 10000);
            socket.setSoTimeout(10000);

            doc = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            byte[] response = new byte[128];
            doc.write(msgPackage.getBytes("GBK"));
            doc.flush();
            in.read(response, 0, 4);
            int msglen = byteToInt2(response);
            int cnt = in.read(response, 0, msglen);
            String rltcode = new String(response, 0, msglen, "GBK");
            System.out.println(rltcode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static int byteToInt2(byte[] b) {

        int mask = 0xff;
        int temp = 0;
        int n = 0;
        for (int i = 0; i < 4; i++) {
            n <<= 8;
            temp = b[i] & mask;
            n |= temp;
        }
        return n;
    }
}
