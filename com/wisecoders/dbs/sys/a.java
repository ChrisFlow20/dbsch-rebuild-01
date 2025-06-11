package com.wisecoders.dbs.sys;

class a implements Comparable {
  public final String a;
  
  public long b;
  
  public int c;
  
  public a(String paramString, long paramLong, int paramInt) {
    this.a = paramString;
    this.b = paramLong;
    this.c = paramInt;
  }
  
  public int a(a parama) {
    return -1 * Long.compare(this.b, parama.b);
  }
}
