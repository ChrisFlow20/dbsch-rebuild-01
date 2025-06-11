package com.wisecoders.dbs.cli.command.sql.transfer;

import com.wisecoders.dbs.config.system.Sys;
import java.util.Formatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TransferQueues {
  private final ArrayBlockingQueue c;
  
  private final ArrayBlockingQueue d;
  
  private final int e;
  
  final int a;
  
  public final int b;
  
  private final long f = System.currentTimeMillis();
  
  private long g;
  
  private long h;
  
  private long i;
  
  private volatile boolean j = false;
  
  private static boolean k = false;
  
  public TransferQueues(int paramInt) {
    k = false;
    if (paramInt < 50) {
      this.a = (50 - paramInt) * 1000;
      this.b = 50 - paramInt;
    } else {
      this.a = 500;
      this.b = 10;
    } 
    this.e = Sys.B.transferParallel.a() + 1;
    this.c = new ArrayBlockingQueue(this.e);
    this.d = new ArrayBlockingQueue(this.e);
  }
  
  public void a() {
    for (byte b = 0; b < this.e; b++)
      this.c.add(new TransferBuffer(this)); 
  }
  
  public void a(TransferBuffer paramTransferBuffer) {
    paramTransferBuffer.d();
    try {
      this.c.put(paramTransferBuffer);
    } catch (InterruptedException interruptedException) {}
  }
  
  public void b(TransferBuffer paramTransferBuffer) {
    try {
      this.d.put(paramTransferBuffer);
    } catch (InterruptedException interruptedException) {}
  }
  
  public TransferBuffer b() {
    TransferBuffer transferBuffer = null;
    long l = System.currentTimeMillis();
    do {
      try {
        transferBuffer = this.c.poll(1L, TimeUnit.SECONDS);
      } catch (InterruptedException interruptedException) {}
    } while (transferBuffer == null && !k);
    this.g += System.currentTimeMillis() - l;
    return transferBuffer;
  }
  
  public TransferBuffer c() {
    TransferBuffer transferBuffer = null;
    long l = System.currentTimeMillis();
    do {
      try {
        transferBuffer = this.d.poll(1L, TimeUnit.SECONDS);
      } catch (InterruptedException interruptedException) {}
    } while (transferBuffer == null && !this.j && !k);
    this.h += System.currentTimeMillis() - l;
    if (transferBuffer != null)
      this.i += transferBuffer.c(); 
    return transferBuffer;
  }
  
  public void d() {
    this.j = true;
  }
  
  public static void e() {
    k = true;
  }
  
  public static void f() {
    k = false;
  }
  
  public static boolean g() {
    return k;
  }
  
  public static String a(long paramLong) {
    long l1 = paramLong / 1000L;
    long l2 = l1 % 60L;
    long l3 = l1 / 60L % 60L;
    long l4 = l1 / 3600L;
    if (l4 > 0L)
      return (new Formatter()).format("%02d:%02d:%02d", new Object[] { Long.valueOf(l4), Long.valueOf(l3), Long.valueOf(l2) }).toString(); 
    return (new Formatter()).format("%02d:%02d", new Object[] { Long.valueOf(l3), Long.valueOf(l2) }).toString();
  }
  
  public String h() {
    return "" + this.i + " rows in " + this.i + ". Reader waited " + 
      a(System.currentTimeMillis() - this.f) + ", writer " + 
      a(this.g) + ".";
  }
  
  public long i() {
    return this.i;
  }
  
  public long j() {
    return this.g;
  }
  
  public long k() {
    return this.h;
  }
  
  public boolean l() {
    return !this.d.isEmpty();
  }
  
  public void m() {
    this.c.clear();
    this.d.clear();
  }
}
