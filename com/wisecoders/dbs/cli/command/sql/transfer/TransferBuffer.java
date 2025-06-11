package com.wisecoders.dbs.cli.command.sql.transfer;

import java.util.Formatter;

public class TransferBuffer {
  public final TransferQueues a;
  
  private final Object[][] b;
  
  private int c = 0, d = 0;
  
  public TransferBuffer(TransferQueues paramTransferQueues) {
    this.a = paramTransferQueues;
    this.b = new Object[paramTransferQueues.a][];
  }
  
  public boolean a(Object[] paramArrayOfObject) {
    this.b[this.c++] = paramArrayOfObject;
    return e();
  }
  
  public Object[] a() {
    if (this.d < this.c)
      return this.b[this.d++]; 
    return null;
  }
  
  public int a(Object[] paramArrayOfObject, int paramInt) {
    if (this.d < this.c && paramInt > 0) {
      int i = Math.min(this.c - this.d, paramInt);
      System.arraycopy(this.b, this.d, paramArrayOfObject, 0, i);
      this.d += i;
      return i;
    } 
    return 0;
  }
  
  public Object[] b() {
    return !e() ? this.b[this.c] : null;
  }
  
  public int c() {
    return this.c;
  }
  
  public void d() {
    this.c = this.d = 0;
    for (byte b = 0; b < this.b.length; b++)
      this.b[b] = null; 
  }
  
  public boolean e() {
    return (this.c >= this.a.a);
  }
  
  public static String a(long paramLong) {
    long l1 = paramLong / 1000L;
    long l2 = l1 % 60L;
    long l3 = l1 / 60L % 60L;
    long l4 = l1 / 3600L;
    return (new Formatter()).format("%02d:%02d:%02d", new Object[] { Long.valueOf(l4), Long.valueOf(l3), Long.valueOf(l2) }).toString();
  }
}
