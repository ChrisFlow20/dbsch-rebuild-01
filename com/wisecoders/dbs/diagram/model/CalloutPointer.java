package com.wisecoders.dbs.diagram.model;

public enum CalloutPointer {
  a(0),
  b(1),
  c(3),
  d(2),
  e(-1),
  f(-1);
  
  private final int g;
  
  CalloutPointer(int paramInt1) {
    this.g = paramInt1;
  }
  
  public int a() {
    return this.g;
  }
  
  public boolean b() {
    return (this == a || this == b);
  }
}
