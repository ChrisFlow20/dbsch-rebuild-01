package com.wisecoders.dbs.schema;

public enum RelationType {
  a(0),
  b(1048576),
  c(2097152);
  
  public final int d;
  
  public static final int e = 15728640;
  
  RelationType(int paramInt1) {
    this.d = paramInt1;
  }
  
  public static RelationType a(int paramInt) {
    paramInt &= 0xF00000;
    if (paramInt == b.d)
      return b; 
    if (paramInt == a.d)
      return a; 
    return c;
  }
}
