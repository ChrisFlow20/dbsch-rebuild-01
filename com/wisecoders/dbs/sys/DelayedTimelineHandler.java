package com.wisecoders.dbs.sys;

public class DelayedTimelineHandler implements Runnable {
  private final long a;
  
  private final Runnable b;
  
  private long c = -1L;
  
  DelayedTimelineHandler(long paramLong, Runnable paramRunnable) {
    this.a = paramLong;
    this.b = paramRunnable;
  }
  
  public void a() {
    this.c = System.currentTimeMillis();
  }
  
  public void b() {
    this.c = -1L;
  }
  
  public void run() {
    if (this.c == -1L || System.currentTimeMillis() - this.c > this.a) {
      this.c = System.currentTimeMillis();
      this.b.run();
    } 
  }
}
