package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.List;

class b extends Level {
  public List a = new UniqueArrayList();
  
  public final int b;
  
  private final b h;
  
  private b i;
  
  public double c = 0.0D;
  
  public double d = 0.0D;
  
  public double e = 0.0D;
  
  public double f = 0.0D;
  
  b(ArrangerDepictTree paramArrangerDepictTree, Depict paramDepict) {
    this.b = 0;
    this.h = null;
    a(paramDepict);
    this.c = paramDepict.getPosition().a();
    this.d = paramDepict.getPosition().b();
    this.e = paramDepict.getPosition().c();
    this.f = paramDepict.getPosition().d();
  }
  
  b(ArrangerDepictTree paramArrangerDepictTree, b paramb, int paramInt) {
    this.h = paramb;
    this.b = paramInt;
  }
  
  public void a(Depict paramDepict) {
    this.a.add(paramDepict);
    this.e = Math.max(this.e, paramDepict.getPosition().c());
    this.f = Math.max(this.f, paramDepict.getPosition().d());
    for (Relation relation : paramDepict.getEntity().getImportedRelations()) {
      if (!relation.isVirtual()) {
        Depict depict = this.g.b(relation.getEntity());
        if (depict != null && this.b < 30) {
          if (this.i == null)
            this.i = new b(this.g, this, this.b + 1); 
          this.i.a(depict);
        } 
      } 
    } 
  }
  
  public void a() {
    if (this.h != null) {
      this.c = this.h.c + this.h.e + (this.g.b.d * Diagram.cell);
      this.d = this.h.d;
      double d = this.h.d;
      for (Depict depict1 : this.a) {
        Depict depict2 = this.g.b(this.g.a(depict1.entity));
        if (depict2 != null)
          d = Math.max(d, depict2.getPosition().b()); 
        depict1.moveTo(this.c, d);
        d += depict1.getPosition().d() + Diagram.cell;
      } 
    } 
    if (this.i != null)
      this.i.a(); 
  }
  
  public void b() {
    if (this.h != null) {
      this.c = this.h.c;
      this.d = this.h.d + this.h.f + (this.g.b.d * Diagram.cell);
      int i = 0;
      for (Depict depict : this.a) {
        if (i)
          i += Diagram.cell; 
        i = (int)(i + depict.getPosition().c());
      } 
      if (i > 0)
        i /= 2; 
      double d = this.h.c + this.h.e / 2.0D;
      for (Depict depict : this.a) {
        depict.moveTo(d - i, this.d);
        d += depict.getPosition().c() + (3 * Diagram.cell);
      } 
    } 
    if (this.i != null)
      this.i.b(); 
  }
}
