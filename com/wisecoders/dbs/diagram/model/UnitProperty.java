package com.wisecoders.dbs.diagram.model;

public enum UnitProperty {
  a(0, false),
  b(1, false),
  c(2, false),
  d(3, false),
  e(4, false),
  f(5, true),
  g(6, true);
  
  public final boolean h;
  
  public final int i;
  
  UnitProperty(int paramInt1, boolean paramBoolean) {
    this.h = paramBoolean;
    this.i = paramInt1;
  }
}
