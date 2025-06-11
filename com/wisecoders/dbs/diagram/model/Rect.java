package com.wisecoders.dbs.diagram.model;

public class Rect {
  private double a;
  
  private double b;
  
  private double c;
  
  private double d;
  
  public Rect() {}
  
  public Rect(Rect paramRect) {
    this.a = paramRect.a;
    this.b = paramRect.b;
    this.c = paramRect.c;
    this.d = paramRect.d;
  }
  
  public Rect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.a = paramDouble1;
    this.b = paramDouble2;
    this.d = paramDouble4;
    this.c = paramDouble3;
  }
  
  public void a(double paramDouble1, double paramDouble2) {
    this.a = paramDouble1;
    this.b = paramDouble2;
  }
  
  public void a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.a = paramDouble1;
    this.b = paramDouble2;
    this.c = paramDouble3;
    this.d = paramDouble4;
  }
  
  public void a(Rect paramRect) {
    this.a = paramRect.a();
    this.b = paramRect.b();
    this.c = paramRect.c();
    this.d = paramRect.d();
  }
  
  public void b(double paramDouble1, double paramDouble2) {
    this.c = paramDouble1;
    this.d = paramDouble2;
  }
  
  public void c(double paramDouble1, double paramDouble2) {
    this.a += paramDouble1;
    this.b += paramDouble2;
  }
  
  public void a(int paramInt) {
    this.a /= paramInt;
    this.b /= paramInt;
    this.c /= paramInt;
    this.d /= paramInt;
  }
  
  public boolean b(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    double d1 = this.c;
    double d2 = this.d;
    if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D || d1 <= 0.0D || d2 <= 0.0D)
      return false; 
    double d3 = this.a;
    double d4 = this.b;
    paramDouble3 += paramDouble1;
    paramDouble4 += paramDouble2;
    d1 += d3;
    d2 += d4;
    return ((paramDouble3 < paramDouble1 || paramDouble3 > d3) && (paramDouble4 < paramDouble2 || paramDouble4 > d4) && (d1 < d3 || d1 > paramDouble1) && (d2 < d4 || d2 > paramDouble2));
  }
  
  public boolean d(double paramDouble1, double paramDouble2) {
    double d1 = this.d;
    if (paramDouble2 <= 0.0D || d1 <= 0.0D)
      return false; 
    double d2 = this.b;
    paramDouble2 += paramDouble1;
    d1 += d2;
    return ((paramDouble2 < paramDouble1 || paramDouble2 > d2) && (d1 < d2 || d1 > paramDouble1));
  }
  
  public boolean b(Rect paramRect) {
    return b(paramRect.a(), paramRect.b(), paramRect.c(), paramRect.d());
  }
  
  public void e(double paramDouble1, double paramDouble2) {
    double d1 = this.a;
    double d2 = this.b;
    double d3 = this.c;
    double d4 = this.d;
    d3 += d1;
    d4 += d2;
    d1 -= paramDouble1;
    d2 -= paramDouble2;
    d3 += paramDouble1;
    d4 += paramDouble2;
    if (d3 < d1) {
      d3 -= d1;
      if (d3 < -2.147483648E9D)
        d3 = -2.147483648E9D; 
      if (d1 < -2.147483648E9D) {
        d1 = -2.147483648E9D;
      } else if (d1 > 2.147483647E9D) {
        d1 = 2.147483647E9D;
      } 
    } else {
      if (d1 < -2.147483648E9D) {
        d1 = -2.147483648E9D;
      } else if (d1 > 2.147483647E9D) {
        d1 = 2.147483647E9D;
      } 
      d3 -= d1;
      if (d3 < -2.147483648E9D) {
        d3 = -2.147483648E9D;
      } else if (d3 > 2.147483647E9D) {
        d3 = 2.147483647E9D;
      } 
    } 
    if (d4 < d2) {
      d4 -= d2;
      if (d4 < -2.147483648E9D)
        d4 = -2.147483648E9D; 
      if (d2 < -2.147483648E9D) {
        d2 = -2.147483648E9D;
      } else if (d2 > 2.147483647E9D) {
        d2 = 2.147483647E9D;
      } 
    } else {
      if (d2 < -2.147483648E9D) {
        d2 = -2.147483648E9D;
      } else if (d2 > 2.147483647E9D) {
        d2 = 2.147483647E9D;
      } 
      d4 -= d2;
      if (d4 < -2.147483648E9D) {
        d4 = -2.147483648E9D;
      } else if (d4 > 2.147483647E9D) {
        d4 = 2.147483647E9D;
      } 
    } 
    a((int)d1, (int)d2, (int)d3, (int)d4);
  }
  
  public void c(Rect paramRect) {
    double d1 = this.c;
    double d2 = this.d;
    if (d1 < 0.0D || d2 < 0.0D)
      a(paramRect.a, paramRect.b, paramRect.c, paramRect.d); 
    double d3 = paramRect.c;
    double d4 = paramRect.d;
    double d5 = this.a;
    double d6 = this.b;
    d1 += d5;
    d2 += d6;
    double d7 = paramRect.a;
    double d8 = paramRect.b;
    d3 += d7;
    d4 += d8;
    if (d5 > d7)
      d5 = d7; 
    if (d6 > d8)
      d6 = d8; 
    if (d1 < d3)
      d1 = d3; 
    if (d2 < d4)
      d2 = d4; 
    d1 -= d5;
    d2 -= d6;
    if (d1 > 2.147483647E9D)
      d1 = 2.147483647E9D; 
    if (d2 > 2.147483647E9D)
      d2 = 2.147483647E9D; 
    a(d5, d6, (int)d1, (int)d2);
  }
  
  public double a() {
    return this.a;
  }
  
  public void a(double paramDouble) {
    this.a = paramDouble;
  }
  
  public double b() {
    return this.b;
  }
  
  public void b(double paramDouble) {
    this.b = paramDouble;
  }
  
  public double c() {
    return this.c;
  }
  
  public void c(double paramDouble) {
    this.c = paramDouble;
  }
  
  public double d() {
    return this.d;
  }
  
  public void d(double paramDouble) {
    this.d = paramDouble;
  }
  
  public int e() {
    return (int)this.a;
  }
  
  public int f() {
    return (int)this.b;
  }
  
  public int g() {
    return (int)this.c;
  }
  
  public int h() {
    return (int)this.d;
  }
  
  public boolean d(Rect paramRect) {
    return c(paramRect.a, paramRect.b, paramRect.c(), paramRect.d());
  }
  
  public boolean a(Point paramPoint) {
    return f(paramPoint.a, paramPoint.b);
  }
  
  public boolean c(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    double d1 = this.c;
    double d2 = this.d;
    if (d1 < 0.0D || d2 < 0.0D || paramDouble3 < 0.0D || paramDouble4 < 0.0D)
      return false; 
    double d3 = this.a;
    double d4 = this.b;
    if (paramDouble1 < d3 || paramDouble2 < d4)
      return false; 
    d1 += d3;
    paramDouble3 += paramDouble1;
    if (paramDouble3 <= paramDouble1) {
      if (d1 >= d3 || paramDouble3 > d1)
        return false; 
    } else if (d1 >= d3 && paramDouble3 > d1) {
      return false;
    } 
    d2 += d4;
    paramDouble4 += paramDouble2;
    if (paramDouble4 <= paramDouble2) {
      if (d2 >= d4 || paramDouble4 > d2)
        return false; 
    } else if (d2 >= d4 && paramDouble4 > d2) {
      return false;
    } 
    return true;
  }
  
  public boolean f(double paramDouble1, double paramDouble2) {
    return g(paramDouble1, paramDouble2);
  }
  
  public boolean g(double paramDouble1, double paramDouble2) {
    double d1 = this.c;
    double d2 = this.d;
    if ((((d1 < 0.0D) ? 1 : 0) | ((d2 < 0.0D) ? 1 : 0)) != 0)
      return false; 
    double d3 = this.a;
    double d4 = this.b;
    if (paramDouble1 < d3 || paramDouble2 < d4)
      return false; 
    d1 += d3;
    d2 += d4;
    return ((d1 < d3 || d1 > paramDouble1) && (d2 < d4 || d2 > paramDouble2));
  }
  
  public String toString() {
    return getClass().getName() + "[x=" + getClass().getName() + ",y=" + this.a + ",width=" + this.b + ",height=" + this.c + "]";
  }
  
  public double i() {
    return this.a + this.c / 2.0D;
  }
  
  public double j() {
    return this.b + this.d / 2.0D;
  }
  
  public Rect e(Rect paramRect) {
    double d1 = this.a;
    double d2 = this.b;
    double d3 = paramRect.a;
    double d4 = paramRect.b;
    double d5 = d1;
    d5 += this.c;
    double d6 = d2;
    d6 += this.d;
    double d7 = d3;
    d7 += paramRect.c;
    double d8 = d4;
    d8 += paramRect.d;
    if (d1 < d3)
      d1 = d3; 
    if (d2 < d4)
      d2 = d4; 
    if (d5 > d7)
      d5 = d7; 
    if (d6 > d8)
      d6 = d8; 
    d5 -= d1;
    d6 -= d2;
    if (d5 < -2.147483648E9D)
      d5 = -2.147483648E9D; 
    if (d6 < -2.147483648E9D)
      d6 = -2.147483648E9D; 
    return new Rect(d1, d2, (int)d5, (int)d6);
  }
  
  public double f(Rect paramRect) {
    boolean bool1 = (paramRect.a + paramRect.c < this.a) ? true : false;
    boolean bool2 = (this.a + this.d < paramRect.a) ? true : false;
    boolean bool3 = (paramRect.b + paramRect.d < this.b) ? true : false;
    boolean bool4 = (this.b + this.d < paramRect.b) ? true : false;
    if (bool4 && bool1)
      return d(this.a, this.b + this.d, paramRect.a + paramRect.c, paramRect.b); 
    if (bool1 && bool3)
      return d(this.a, this.b, paramRect.a + paramRect.c(), paramRect.b + paramRect.d); 
    if (bool3 && bool2)
      return d(this.a + this.c, this.b, paramRect.a, paramRect.b + paramRect.d); 
    if (bool2 && bool4)
      return d(this.a + this.c, this.b + this.d, paramRect.a, paramRect.b); 
    if (bool1)
      return this.a - paramRect.a + paramRect.c; 
    if (bool2)
      return paramRect.a - this.a + this.c; 
    if (bool3)
      return this.b - paramRect.b + paramRect.d; 
    if (bool4)
      return paramRect.b - this.b + this.d; 
    return 0.0D;
  }
  
  public static double d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    return Math.sqrt((paramDouble4 - paramDouble2) * (paramDouble4 - paramDouble2) + (paramDouble3 - paramDouble1) * (paramDouble3 - paramDouble1));
  }
}
