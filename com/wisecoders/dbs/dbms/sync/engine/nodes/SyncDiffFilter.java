package com.wisecoders.dbs.dbms.sync.engine.nodes;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.StringUtil;

public class SyncDiffFilter {
  private boolean a = true;
  
  private boolean b = true;
  
  private boolean c = true;
  
  private boolean d = true;
  
  private boolean e = true;
  
  private boolean f = true;
  
  private boolean g = true;
  
  private boolean h = true;
  
  private boolean i = true;
  
  private boolean j = true;
  
  private boolean k = true;
  
  private String l;
  
  private boolean m = false;
  
  private boolean n = false;
  
  private String o;
  
  private boolean p = false;
  
  public void a(String paramString) {
    this.o = paramString;
    String str = Pref.a(paramString);
    this.p = StringUtil.isFilled(str);
    d(str);
  }
  
  public void a() {
    if (this.p) {
      if (this.o != null)
        Pref.a(this.o, toString()); 
    } else {
      Pref.b(this.o);
    } 
  }
  
  public boolean b() {
    return this.a;
  }
  
  public void a(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public boolean c() {
    return this.b;
  }
  
  public void b(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public boolean d() {
    return this.c;
  }
  
  public void c(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public boolean e() {
    return this.d;
  }
  
  public void d(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public boolean f() {
    return this.e;
  }
  
  public void e(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  public boolean g() {
    return this.f;
  }
  
  public void f(boolean paramBoolean) {
    this.f = paramBoolean;
  }
  
  public boolean h() {
    return this.g;
  }
  
  public void g(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public boolean i() {
    return this.h;
  }
  
  public void h(boolean paramBoolean) {
    this.h = paramBoolean;
  }
  
  public boolean j() {
    return this.i;
  }
  
  public void i(boolean paramBoolean) {
    this.i = paramBoolean;
  }
  
  public boolean k() {
    return this.j;
  }
  
  public void j(boolean paramBoolean) {
    this.j = paramBoolean;
  }
  
  public boolean l() {
    return this.k;
  }
  
  public void k(boolean paramBoolean) {
    this.k = paramBoolean;
  }
  
  public String m() {
    return this.l;
  }
  
  public void b(String paramString) {
    this.l = paramString;
  }
  
  public boolean n() {
    return this.m;
  }
  
  public void l(boolean paramBoolean) {
    this.m = paramBoolean;
  }
  
  public boolean o() {
    return this.n;
  }
  
  public void m(boolean paramBoolean) {
    this.n = paramBoolean;
  }
  
  public boolean a(AbstractUnit... paramVarArgs) {
    for (AbstractUnit abstractUnit : paramVarArgs) {
      if (abstractUnit != null && c(abstractUnit.getName()))
        return true; 
    } 
    return false;
  }
  
  public boolean c(String paramString) {
    return (this.l == null || paramString == null || paramString.toLowerCase().contains(this.l));
  }
  
  public void n(boolean paramBoolean) {
    this.p = paramBoolean;
  }
  
  public boolean p() {
    return this.p;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (!this.a || !this.c || !this.e || !this.d || !this.f || !this.g || !this.h || !this.i || !this.j) {
      stringBuilder.append("hide=");
      if (!this.a)
        stringBuilder.append("t"); 
      if (!this.b)
        stringBuilder.append("v"); 
      if (!this.c)
        stringBuilder.append("c"); 
      if (!this.e)
        stringBuilder.append("f"); 
      if (!this.d)
        stringBuilder.append("i"); 
      if (!this.f)
        stringBuilder.append("k"); 
      if (!this.g)
        stringBuilder.append("S"); 
      if (!this.h)
        stringBuilder.append("T"); 
      if (!this.i)
        stringBuilder.append("P"); 
      if (!this.j)
        stringBuilder.append("F"); 
      if (!this.k)
        stringBuilder.append("m"); 
      stringBuilder.append(";");
    } 
    if (this.m || this.n) {
      stringBuilder.append("exists=");
      if (this.m)
        stringBuilder.append("l"); 
      if (this.n)
        stringBuilder.append("r"); 
      stringBuilder.append(";");
    } 
    if (this.l != null)
      stringBuilder.append("text=").append(this.l); 
    return stringBuilder.toString();
  }
  
  public void d(String paramString) {
    if (paramString != null)
      for (String str : paramString.split(";")) {
        if (str.startsWith("hide=")) {
          String str1 = str.split("=")[1];
          if (str1.contains("t"))
            this.a = false; 
          if (str1.contains("v"))
            this.b = false; 
          if (str1.contains("c"))
            this.c = false; 
          if (str1.contains("f"))
            this.e = false; 
          if (str1.contains("i"))
            this.d = false; 
          if (str1.contains("k"))
            this.f = false; 
          if (str1.contains("S"))
            this.g = false; 
          if (str1.contains("T"))
            this.h = false; 
          if (str1.contains("P"))
            this.i = false; 
          if (str1.contains("F"))
            this.j = false; 
          if (str1.contains("m"))
            this.k = false; 
        } else if (str.startsWith("exists=")) {
          String str1 = str.split("=")[1];
          if (str1.contains("l"))
            this.m = true; 
          if (str1.contains("r"))
            this.n = true; 
        } else if (str.startsWith("text=")) {
          this.l = str.split("=")[1];
        } 
      }  
  }
}
