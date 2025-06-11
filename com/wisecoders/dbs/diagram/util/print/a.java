package com.wisecoders.dbs.diagram.util.print;

import java.io.OutputStream;

class a {
  private static final int t = -1;
  
  private int u;
  
  private int v;
  
  private byte[] w;
  
  private int x;
  
  private int y;
  
  private int z;
  
  static final int a = 12;
  
  static final int b = 5003;
  
  int c;
  
  int d = 12;
  
  int e;
  
  int f = 4096;
  
  int[] g = new int[5003];
  
  int[] h = new int[5003];
  
  int i = 5003;
  
  int j = 0;
  
  boolean k = false;
  
  int l;
  
  int m;
  
  int n;
  
  int o = 0;
  
  int p = 0;
  
  int[] q = new int[] { 
      0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 
      1023, 2047, 4095, 8191, 16383, 32767, 65535 };
  
  int r;
  
  byte[] s = new byte[256];
  
  a(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3) {
    this.u = paramInt1;
    this.v = paramInt2;
    this.w = paramArrayOfbyte;
    this.x = Math.max(2, paramInt3);
  }
  
  void a(byte paramByte, OutputStream paramOutputStream) {
    this.s[this.r++] = paramByte;
    if (this.r >= 254)
      c(paramOutputStream); 
  }
  
  void a(OutputStream paramOutputStream) {
    a(this.i);
    this.j = this.m + 2;
    this.k = true;
    b(this.m, paramOutputStream);
  }
  
  void a(int paramInt) {
    for (byte b = 0; b < paramInt; b++)
      this.g[b] = -1; 
  }
  
  void a(int paramInt, OutputStream paramOutputStream) {
    this.l = paramInt;
    this.k = false;
    this.c = this.l;
    this.e = b(this.c);
    this.m = 1 << paramInt - 1;
    this.n = this.m + 1;
    this.j = this.m + 2;
    this.r = 0;
    int k = a();
    int n = 0;
    int i;
    for (i = this.i; i < 65536; i *= 2)
      n++; 
    n = 8 - n;
    int m = this.i;
    a(m);
    b(this.m, paramOutputStream);
    int j;
    label35: while ((j = a()) != -1) {
      i = (j << this.d) + k;
      int i1 = j << n ^ k;
      if (this.g[i1] == i) {
        k = this.h[i1];
        continue;
      } 
      if (this.g[i1] >= 0) {
        int i2 = m - i1;
        if (i1 == 0)
          i2 = 1; 
        do {
          if ((i1 -= i2) < 0)
            i1 += m; 
          if (this.g[i1] == i) {
            k = this.h[i1];
            continue label35;
          } 
        } while (this.g[i1] >= 0);
      } 
      b(k, paramOutputStream);
      k = j;
      if (this.j < this.f) {
        this.h[i1] = this.j++;
        this.g[i1] = i;
        continue;
      } 
      a(paramOutputStream);
    } 
    b(k, paramOutputStream);
    b(this.n, paramOutputStream);
  }
  
  void b(OutputStream paramOutputStream) {
    paramOutputStream.write(this.x);
    this.y = this.u * this.v;
    this.z = 0;
    a(this.x + 1, paramOutputStream);
    paramOutputStream.write(0);
  }
  
  void c(OutputStream paramOutputStream) {
    if (this.r > 0) {
      paramOutputStream.write(this.r);
      paramOutputStream.write(this.s, 0, this.r);
      this.r = 0;
    } 
  }
  
  final int b(int paramInt) {
    return (1 << paramInt) - 1;
  }
  
  private int a() {
    if (this.y == 0)
      return -1; 
    this.y--;
    byte b = this.w[this.z++];
    return b & 0xFF;
  }
  
  void b(int paramInt, OutputStream paramOutputStream) {
    this.o &= this.q[this.p];
    if (this.p > 0) {
      this.o |= paramInt << this.p;
    } else {
      this.o = paramInt;
    } 
    this.p += this.c;
    while (this.p >= 8) {
      a((byte)(this.o & 0xFF), paramOutputStream);
      this.o >>= 8;
      this.p -= 8;
    } 
    if (this.j > this.e || this.k)
      if (this.k) {
        this.e = b(this.c = this.l);
        this.k = false;
      } else {
        this.c++;
        if (this.c == this.d) {
          this.e = this.f;
        } else {
          this.e = b(this.c);
        } 
      }  
    if (paramInt == this.n) {
      while (this.p > 0) {
        a((byte)(this.o & 0xFF), paramOutputStream);
        this.o >>= 8;
        this.p -= 8;
      } 
      c(paramOutputStream);
    } 
  }
}
