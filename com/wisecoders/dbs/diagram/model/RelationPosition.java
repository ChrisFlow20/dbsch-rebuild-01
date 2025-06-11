package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;

public class RelationPosition {
  public final int a;
  
  public final int b;
  
  public final int c;
  
  public final int d;
  
  public final int e;
  
  public final int f;
  
  public final int g;
  
  public final String h;
  
  private int i;
  
  private int j;
  
  private int k;
  
  private int l;
  
  public RelationPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.d = paramInt4;
    this.e = paramInt5;
    this.f = paramInt6;
    this.h = paramString;
    this.g = paramInt3;
  }
  
  private synchronized void g() {
    if (this.i == 0) {
      int i = (int)FxAbstractDiagramPane.E().getSize();
      int j = i / 2;
      int k = i / 4;
      int m = this.a * Diagram.cell + k;
      int n = this.b * Diagram.cell + k;
      this.i = FxAbstractDiagramPane.a(this.h);
      this.j = (int)FxAbstractDiagramPane.E().getSize();
      switch (this.c) {
        case 0:
          n += k;
          break;
        case 1:
          m += j;
          break;
        case 2:
          m += j + k;
          n += j;
          break;
        case 3:
          m -= this.i - j;
          break;
      } 
      this.k = m;
      this.l = n;
    } 
  }
  
  public String toString() {
    return "Pointer( " + this.a + ", " + this.b + ", " + this.c + " -> " + this.d + ", " + this.e + ", " + this.f + " )";
  }
  
  public boolean a(int paramInt1, int paramInt2) {
    byte b = 2;
    boolean bool = false;
    g();
    switch (this.c) {
      case 1:
      case 3:
        bool = (paramInt1 >= this.k - 2 && paramInt1 <= this.k + this.i + 8 && paramInt2 >= this.l - this.j - 2 && paramInt2 <= this.l + 4) ? true : false;
        break;
      case 0:
        bool = (paramInt1 >= this.k - this.j && paramInt1 <= this.a && paramInt2 >= this.l - this.i && paramInt2 <= this.l) ? true : false;
        break;
      case 2:
        bool = (paramInt1 >= this.k && paramInt1 <= this.k + this.j && paramInt2 >= this.l && paramInt2 <= this.l + this.i) ? true : false;
        break;
    } 
    return bool;
  }
  
  public double a() {
    switch (this.c) {
      case 0:
      
      case 2:
      
    } 
    return 

      
      0.0D;
  }
  
  public int b() {
    switch (this.c) {
      case 0:
      
      case 2:
      
    } 
    return 

      
      0;
  }
  
  public int c() {
    g();
    return this.i;
  }
  
  public int d() {
    g();
    return this.j;
  }
  
  public int e() {
    g();
    return this.k;
  }
  
  public int f() {
    g();
    return this.l;
  }
}
