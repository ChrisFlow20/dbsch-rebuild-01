package com.wisecoders.dbs.generator.engine.plan;

import java.util.ArrayList;
import java.util.List;

public class DefinedPattern {
  public final Category a;
  
  private String c;
  
  private int d;
  
  protected String b;
  
  private String e;
  
  private final List f = new ArrayList();
  
  public DefinedPattern(Category paramCategory, String paramString1, String paramString2) {
    this.a = paramCategory;
    this.c = paramString1;
    this.b = paramString2;
  }
  
  public void a(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4) {
    this.f.add(new PatternMatcher(this, paramString1, paramString2, paramInt, paramString3, paramString4));
  }
  
  public List a() {
    return this.f;
  }
  
  public String b() {
    return this.c;
  }
  
  public void a(String paramString) {
    this.c = paramString;
  }
  
  public String c() {
    return this.e;
  }
  
  public void b(String paramString) {
    this.e = paramString;
  }
  
  public void a(int paramInt) {
    this.d = paramInt;
  }
  
  public int d() {
    return this.d;
  }
  
  public String e() {
    return this.b;
  }
  
  public void c(String paramString) {
    if (paramString == null) {
      this.b = "";
    } else {
      this.b = paramString;
    } 
  }
  
  public String toString() {
    return this.c;
  }
}
