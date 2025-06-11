package com.wisecoders.dbs.sys;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BroadcastWriter {
  public static void main(String[] paramArrayOfString) {
    String str = "This is a test";
    (new BroadcastWriter()).a(str);
  }
  
  private synchronized void a(String paramString) {
    byte b = 0;
    while (true) {
      try {
        while (true) {
          InetAddress inetAddress = InetAddress.getByName("255.255.255.255");
          DatagramSocket datagramSocket = new DatagramSocket();
          String str = paramString + paramString;
          DatagramPacket datagramPacket = new DatagramPacket(str.getBytes(), str.length(), inetAddress, 9090);
          datagramSocket.send(datagramPacket);
          datagramSocket.close();
          Thread.currentThread().wait(1000L);
          b++;
        } 
        break;
      } catch (UnknownHostException unknownHostException) {
        unknownHostException.printStackTrace();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
      } 
    } 
  }
}
