package com.wisecoders.dbs.sys;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BroadcastReader {
  public static final int a = 9090;
  
  public static void main(String[] paramArrayOfString) {
    byte[] arrayOfByte = new byte[1024];
    StringBuilder stringBuilder = new StringBuilder();
    try {
      DatagramSocket datagramSocket = new DatagramSocket(9090);
      try {
        DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length);
        System.out.println("The listening broadcast port is open:");
        while (true) {
          datagramSocket.receive(datagramPacket);
          for (byte b = 0; b < arrayOfByte.length && arrayOfByte[b] != 0; b++)
            stringBuilder.append((char)arrayOfByte[b]); 
          System.out.println("Received broadcast message:" + stringBuilder.toString());
        } 
      } catch (Throwable throwable) {
        try {
          datagramSocket.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
      return;
    } 
  }
}
