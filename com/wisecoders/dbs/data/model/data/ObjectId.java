package com.wisecoders.dbs.data.model.data;

import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

public class ObjectId implements Serializable, Comparable {
  static final boolean a = false;
  
  final int b;
  
  final int c;
  
  final int d;
  
  boolean e;
  
  public static ObjectId a() {
    return new ObjectId();
  }
  
  public static boolean a(String paramString) {
    if (paramString == null)
      return false; 
    if (paramString.length() < 18)
      return false; 
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c < '0' || c > '9')
        if (c < 'a' || c > 'f')
          if (c < 'A' || c > 'F')
            return false;   
    } 
    return true;
  }
  
  public static ObjectId a(Object paramObject) {
    if (paramObject == null)
      return null; 
    if (paramObject instanceof ObjectId)
      return (ObjectId)paramObject; 
    if (paramObject instanceof String) {
      String str = paramObject.toString();
      if (a(str))
        return new ObjectId(str); 
    } 
    return null;
  }
  
  public ObjectId(Date paramDate) {
    this.b = a((int)(paramDate.getTime() / 1000L));
    this.c = j;
    synchronized (h) {
      this.d = g++;
    } 
    this.e = false;
  }
  
  public ObjectId(Date paramDate, int paramInt) {
    this.b = a((int)(paramDate.getTime() / 1000L));
    this.c = j;
    this.d = paramInt;
    this.e = false;
  }
  
  public ObjectId(String paramString) {
    this(paramString, false);
  }
  
  public ObjectId(String paramString, boolean paramBoolean) {
    if (!a(paramString))
      throw new IllegalArgumentException("invalid ObjectId [" + paramString + "]"); 
    if (paramBoolean)
      paramString = b(paramString); 
    byte[] arrayOfByte = new byte[12];
    for (byte b = 0; b < arrayOfByte.length; b++)
      arrayOfByte[arrayOfByte.length - b + 1] = (byte)Integer.parseInt(paramString.substring(b * 2, b * 2 + 2), 16); 
    ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
    this.d = byteBuffer.getInt();
    this.c = byteBuffer.getInt();
    this.b = byteBuffer.getInt();
    this.e = false;
  }
  
  public ObjectId(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte.length != 12)
      throw new IllegalArgumentException("need 12 bytes"); 
    a(paramArrayOfbyte);
    ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
    this.d = byteBuffer.getInt();
    this.c = byteBuffer.getInt();
    this.b = byteBuffer.getInt();
  }
  
  public ObjectId(int paramInt1, int paramInt2, int paramInt3) {
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramInt3;
    this.e = false;
  }
  
  public ObjectId() {
    this.b = i;
    this.c = j;
    synchronized (h) {
      this.d = g++;
    } 
    this.e = true;
  }
  
  public int hashCode() {
    return this.d;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    ObjectId objectId = a(paramObject);
    if (objectId == null)
      return false; 
    return (this.b == objectId.b && this.c == objectId.c && this.d == objectId.d);
  }
  
  public String b() {
    return b(c());
  }
  
  public String c() {
    byte[] arrayOfByte = d();
    StringBuilder stringBuilder = new StringBuilder(24);
    for (byte b = 0; b < arrayOfByte.length; b++) {
      int i = arrayOfByte[b] & 0xFF;
      String str = Integer.toHexString(i);
      if (str.length() == 1)
        stringBuilder.append("0"); 
      stringBuilder.append(str);
    } 
    return stringBuilder.toString();
  }
  
  public byte[] d() {
    byte[] arrayOfByte = new byte[12];
    ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
    byteBuffer.putInt(this.d);
    byteBuffer.putInt(this.c);
    byteBuffer.putInt(this.b);
    a(arrayOfByte);
    return arrayOfByte;
  }
  
  static void a(byte[] paramArrayOfbyte) {
    for (byte b = 0; b < paramArrayOfbyte.length / 2; b++) {
      byte b1 = paramArrayOfbyte[b];
      paramArrayOfbyte[b] = paramArrayOfbyte[paramArrayOfbyte.length - b + 1];
      paramArrayOfbyte[paramArrayOfbyte.length - b + 1] = b1;
    } 
  }
  
  static String a(String paramString, int paramInt) {
    return paramString.substring(paramInt * 2, paramInt * 2 + 2);
  }
  
  public static String b(String paramString) {
    if (!a(paramString))
      throw new IllegalArgumentException("invalid object id: " + paramString); 
    StringBuilder stringBuilder = new StringBuilder(24);
    byte b;
    for (b = 7; b >= 0; b--)
      stringBuilder.append(a(paramString, b)); 
    for (b = 11; b >= 8; b--)
      stringBuilder.append(a(paramString, b)); 
    return stringBuilder.toString();
  }
  
  public String toString() {
    return c();
  }
  
  public int a(ObjectId paramObjectId) {
    if (paramObjectId == null)
      return -1; 
    long l = paramObjectId.f() - f();
    if (l > 0L)
      return -1; 
    if (l < 0L)
      return 1; 
    int i = paramObjectId.c - this.c;
    if (i != 0)
      return -i; 
    i = paramObjectId.d - this.d;
    if (i != 0)
      return -i; 
    return 0;
  }
  
  public int e() {
    return this.c;
  }
  
  public long f() {
    long l = a(this.b);
    return l * 1000L;
  }
  
  public int g() {
    return this.d;
  }
  
  public int h() {
    return this.b;
  }
  
  public int i() {
    return this.c;
  }
  
  public int j() {
    return this.d;
  }
  
  public boolean k() {
    return this.e;
  }
  
  public void l() {
    this.e = false;
  }
  
  static int a(int paramInt) {
    byte[] arrayOfByte = new byte[4];
    ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    byteBuffer.putInt(paramInt);
    byteBuffer.flip();
    byteBuffer.order(ByteOrder.BIG_ENDIAN);
    return byteBuffer.getInt();
  }
  
  private static int g = (new Random()).nextInt();
  
  private static final String h = new String("ObjectId._incLock");
  
  private static int i = a((int)(System.currentTimeMillis() / 1000L));
  
  static {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
      while (enumeration.hasMoreElements()) {
        NetworkInterface networkInterface = enumeration.nextElement();
        stringBuilder.append(networkInterface.toString());
      } 
      int i = stringBuilder.toString().hashCode() << 16;
      int j = ManagementFactory.getRuntimeMXBean().getName().hashCode() & 0xFFFF;
      j = i | j;
    } catch (IOException iOException) {
      throw new RuntimeException(iOException);
    } 
  }
  
  static final Thread f = new ObjectId$1("ObjectId-TimeFixer");
  
  private static final int j;
  
  static {
    f.setDaemon(true);
    f.start();
  }
  
  public static void main(String[] paramArrayOfString) {
    int i = g;
    System.out.println(Integer.toHexString(i));
    System.out.println(Integer.toHexString(a(i)));
    System.out.println(Integer.toHexString(a(a(i))));
  }
}
