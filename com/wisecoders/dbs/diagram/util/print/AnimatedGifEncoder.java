package com.wisecoders.dbs.diagram.util.print;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

public class AnimatedGifEncoder {
  protected int a;
  
  protected int b;
  
  protected Color c = null;
  
  protected int d;
  
  protected int e = -1;
  
  protected int f = 0;
  
  protected boolean g = false;
  
  protected OutputStream h;
  
  protected BufferedImage i;
  
  protected byte[] j;
  
  protected byte[] k;
  
  protected int l;
  
  protected byte[] m;
  
  protected boolean[] n = new boolean[256];
  
  protected int o = 7;
  
  protected int p = -1;
  
  protected boolean q = false;
  
  protected boolean r = true;
  
  protected boolean s = false;
  
  protected int t = 10;
  
  public void a(int paramInt) {
    this.f = Math.round(paramInt / 10.0F);
  }
  
  public void b(int paramInt) {
    if (paramInt >= 0)
      this.p = paramInt; 
  }
  
  public void c(int paramInt) {
    if (paramInt >= 0)
      this.e = paramInt; 
  }
  
  public void a(Color paramColor) {
    this.c = paramColor;
  }
  
  public boolean a(BufferedImage paramBufferedImage) {
    if (paramBufferedImage == null || !this.g)
      return false; 
    boolean bool = true;
    try {
      if (!this.s)
        a(paramBufferedImage.getWidth(), paramBufferedImage.getHeight()); 
      this.i = paramBufferedImage;
      c();
      b();
      if (this.r) {
        f();
        h();
        if (this.e >= 0)
          g(); 
      } 
      d();
      e();
      if (!this.r)
        h(); 
      i();
      this.r = false;
    } catch (IOException iOException) {
      bool = false;
    } 
    return bool;
  }
  
  public boolean a() {
    if (!this.g)
      return false; 
    boolean bool = true;
    this.g = false;
    try {
      this.h.write(59);
      this.h.flush();
      if (this.q)
        this.h.close(); 
    } catch (IOException iOException) {
      bool = false;
    } 
    this.d = 0;
    this.h = null;
    this.i = null;
    this.j = null;
    this.k = null;
    this.m = null;
    this.q = false;
    this.r = true;
    return bool;
  }
  
  public void a(float paramFloat) {
    if (paramFloat != 0.0F)
      this.f = Math.round(100.0F / paramFloat); 
  }
  
  public void d(int paramInt) {
    if (paramInt < 1)
      paramInt = 1; 
    this.t = paramInt;
  }
  
  public void a(int paramInt1, int paramInt2) {
    if (this.g && !this.r)
      return; 
    this.a = paramInt1;
    this.b = paramInt2;
    if (this.a < 1)
      this.a = 320; 
    if (this.b < 1)
      this.b = 240; 
    this.s = true;
  }
  
  public boolean a(OutputStream paramOutputStream) {
    if (paramOutputStream == null)
      return false; 
    boolean bool = true;
    this.q = false;
    this.h = paramOutputStream;
    try {
      b("GIF89a");
    } catch (IOException iOException) {
      bool = false;
    } 
    return this.g = bool;
  }
  
  public boolean a(String paramString) {
    boolean bool;
    try {
      this.h = new BufferedOutputStream(new FileOutputStream(Paths.get(paramString, new String[0]).toFile()));
      bool = a(this.h);
      this.q = true;
    } catch (IOException iOException) {
      bool = false;
    } 
    return this.g = bool;
  }
  
  protected void b() {
    int i = this.j.length;
    int j = i / 3;
    this.k = new byte[j];
    b b = new b(this.j, i, this.t);
    this.m = b.d();
    byte b1;
    for (b1 = 0; b1 < this.m.length; b1 += 3) {
      byte b3 = this.m[b1];
      this.m[b1] = this.m[b1 + 2];
      this.m[b1 + 2] = b3;
      this.n[b1 / 3] = false;
    } 
    b1 = 0;
    for (byte b2 = 0; b2 < j; b2++) {
      int k = b.a(this.j[b1++] & 0xFF, this.j[b1++] & 0xFF, this.j[b1++] & 0xFF);
      this.n[k] = true;
      this.k[b2] = (byte)k;
    } 
    this.j = null;
    this.l = 8;
    this.o = 7;
    if (this.c != null)
      this.d = b(this.c); 
  }
  
  protected int b(Color paramColor) {
    if (this.m == null)
      return -1; 
    int i = paramColor.getRed();
    int j = paramColor.getGreen();
    int k = paramColor.getBlue();
    int m = 0;
    int n = 16777216;
    int i1 = this.m.length;
    for (byte b = 0; b < i1; ) {
      int i2 = i - (this.m[b++] & 0xFF);
      int i3 = j - (this.m[b++] & 0xFF);
      int i4 = k - (this.m[b] & 0xFF);
      int i5 = i2 * i2 + i3 * i3 + i4 * i4;
      int i6 = b / 3;
      if (this.n[i6] && i5 < n) {
        n = i5;
        m = i6;
      } 
      b++;
    } 
    return m;
  }
  
  protected void c() {
    int i = this.i.getWidth();
    int j = this.i.getHeight();
    int k = this.i.getType();
    if (i != this.a || j != this.b || k != 5) {
      BufferedImage bufferedImage = new BufferedImage(this.a, this.b, 5);
      Graphics2D graphics2D = bufferedImage.createGraphics();
      graphics2D.drawImage(this.i, 0, 0, null);
      this.i = bufferedImage;
    } 
    this.j = ((DataBufferByte)this.i.getRaster().getDataBuffer()).getData();
  }
  
  protected void d() {
    boolean bool;
    int i;
    this.h.write(33);
    this.h.write(249);
    this.h.write(4);
    if (this.c == null) {
      bool = false;
      i = 0;
    } else {
      bool = true;
      i = 2;
    } 
    if (this.p >= 0)
      i = this.p & 0x7; 
    i <<= 2;
    this.h.write(0x0 | i | 0x0 | bool);
    e(this.f);
    this.h.write(this.d);
    this.h.write(0);
  }
  
  protected void e() {
    this.h.write(44);
    e(0);
    e(0);
    e(this.a);
    e(this.b);
    if (this.r) {
      this.h.write(0);
    } else {
      this.h.write(0x80 | this.o);
    } 
  }
  
  protected void f() {
    e(this.a);
    e(this.b);
    this.h.write(0xF0 | this.o);
    this.h.write(0);
    this.h.write(0);
  }
  
  protected void g() {
    this.h.write(33);
    this.h.write(255);
    this.h.write(11);
    b("NETSCAPE2.0");
    this.h.write(3);
    this.h.write(1);
    e(this.e);
    this.h.write(0);
  }
  
  protected void h() {
    this.h.write(this.m, 0, this.m.length);
    int i = 768 - this.m.length;
    for (byte b = 0; b < i; b++)
      this.h.write(0); 
  }
  
  protected void i() {
    a a = new a(this.a, this.b, this.k, this.l);
    a.b(this.h);
  }
  
  protected void e(int paramInt) {
    this.h.write(paramInt & 0xFF);
    this.h.write(paramInt >> 8 & 0xFF);
  }
  
  protected void b(String paramString) {
    for (byte b = 0; b < paramString.length(); b++)
      this.h.write((byte)paramString.charAt(b)); 
  }
}
