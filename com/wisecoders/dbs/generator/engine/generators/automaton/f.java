package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.Arrays;

final class f {
  static {
    e = new char[0];
    f = new f[0];
  }
  
  char[] a = e;
  
  f[] b = f;
  
  private static final char[] e;
  
  private static final f[] f;
  
  boolean c;
  
  public f a(char paramChar) {
    int i = Arrays.binarySearch(this.a, paramChar);
    return (i >= 0) ? this.b[i] : null;
  }
  
  public char[] a() {
    return this.a;
  }
  
  public f[] b() {
    return this.b;
  }
  
  public boolean equals(Object paramObject) {
    f f1 = (f)paramObject;
    return (this.c == f1.c && 
      Arrays.equals(this.a, f1.a) && 
      a((Object[])this.b, (Object[])f1.b));
  }
  
  public boolean c() {
    return (this.a.length > 0);
  }
  
  public boolean d() {
    return this.c;
  }
  
  public int hashCode() {
    int i = this.c ? 1 : 0;
    i ^= i * 31 + this.a.length;
    for (char c : this.a)
      i ^= i * 31 + c; 
    for (f f1 : this.b)
      i ^= System.identityHashCode(f1); 
    return i;
  }
  
  f b(char paramChar) {
    if (!d && Arrays.binarySearch(this.a, paramChar) >= 0)
      throw new AssertionError("State already has transition labeled: " + paramChar); 
    this.a = a(this.a, this.a.length + 1);
    this.b = a(this.b, this.b.length + 1);
    this.a[this.a.length - 1] = paramChar;
    this.b[this.b.length - 1] = new f();
    return new f();
  }
  
  f e() {
    if (!d && !c())
      throw new AssertionError("No outgoing transitions."); 
    return this.b[this.b.length - 1];
  }
  
  f c(char paramChar) {
    int i = this.a.length - 1;
    f f1 = null;
    if (i >= 0 && this.a[i] == paramChar)
      f1 = this.b[i]; 
    if (!d && f1 != a(paramChar))
      throw new AssertionError(); 
    return f1;
  }
  
  void a(f paramf) {
    if (!d && !c())
      throw new AssertionError("No outgoing transitions."); 
    this.b[this.b.length - 1] = paramf;
  }
  
  private static char[] a(char[] paramArrayOfchar, int paramInt) {
    char[] arrayOfChar = new char[paramInt];
    System.arraycopy(paramArrayOfchar, 0, arrayOfChar, 0, Math.min(paramArrayOfchar.length, paramInt));
    return arrayOfChar;
  }
  
  public static f[] a(f[] paramArrayOff, int paramInt) {
    f[] arrayOfF = new f[paramInt];
    System.arraycopy(paramArrayOff, 0, arrayOfF, 0, Math.min(paramArrayOff.length, paramInt));
    return arrayOfF;
  }
  
  private static boolean a(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2) {
    if (paramArrayOfObject1.length != paramArrayOfObject2.length)
      return false; 
    for (byte b = 0; b < paramArrayOfObject1.length; b++) {
      if (paramArrayOfObject1[b] != paramArrayOfObject2[b])
        return false; 
    } 
    return true;
  }
}
