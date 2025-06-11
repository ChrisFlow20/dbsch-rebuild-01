package com.wisecoders.dbs.schema;

public enum RelationCardinality {
  a(0),
  b(16777216),
  c(33554432),
  d(50331648),
  e(67108864),
  f(83886080);
  
  public static final int g = 251658240;
  
  public final int h;
  
  RelationCardinality(int paramInt1) {
    this.h = paramInt1;
  }
  
  public static RelationCardinality a(int paramInt) {
    paramInt &= 0xF000000;
    if (paramInt == a.h)
      return a; 
    if (paramInt == c.h)
      return c; 
    if (paramInt == b.h)
      return b; 
    if (paramInt == d.h)
      return d; 
    if (paramInt == f.h)
      return f; 
    return e;
  }
  
  public String a(boolean paramBoolean1, boolean paramBoolean2) {
    return (paramBoolean1 ? "Trg" : "Src") + (paramBoolean1 ? "Trg" : "Src") + (paramBoolean2 ? "Many" : "");
  }
}
