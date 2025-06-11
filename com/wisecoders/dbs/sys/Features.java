package com.wisecoders.dbs.sys;

public enum Features {
  a(true, false),
  b(false, false),
  c(false, false),
  d(false, false),
  e(true, false),
  f(true, true),
  g(false, true);
  
  public final boolean h;
  
  public final boolean i;
  
  Features(boolean paramBoolean1, boolean paramBoolean2) {
    this.h = paramBoolean1;
    this.i = paramBoolean2;
  }
}
