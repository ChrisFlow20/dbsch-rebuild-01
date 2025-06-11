package com.wisecoders.dbs.data.model.result;

import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.StringUtil;

public class ResultColumn {
  public final String a;
  
  public final int b;
  
  public final boolean c;
  
  public final int d;
  
  private boolean e = false;
  
  private String f;
  
  private String g;
  
  private Attribute h;
  
  private static final String i = "colWdt_";
  
  public ResultColumn(String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
    this.a = paramString;
    this.d = paramInt1;
    this.b = paramInt2;
    this.c = paramBoolean;
  }
  
  void a(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  void a(String paramString1, String paramString2) {
    this.f = paramString1;
    this.g = paramString2;
  }
  
  public void a(Attribute paramAttribute) {
    this.h = paramAttribute;
  }
  
  public Attribute a() {
    return this.h;
  }
  
  public String b() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(c()).append(".").append(this.b);
    return (stringBuilder.length() > 30) ? (stringBuilder.substring(0, 30) + stringBuilder.substring(0, 30)) : stringBuilder.toString();
  }
  
  public String c() {
    StringBuilder stringBuilder = new StringBuilder();
    if (StringUtil.isFilledTrim(this.f))
      stringBuilder.append(this.f).append("."); 
    if (StringUtil.isFilledTrim(this.g))
      stringBuilder.append(this.g).append("."); 
    if (StringUtil.isFilledTrim(this.a))
      stringBuilder.append(this.a); 
    return stringBuilder.toString();
  }
  
  public void a(double paramDouble) {
    Pref.a("colWdt_" + b(), paramDouble);
  }
  
  public double d() {
    double d = Pref.b("colWdt_" + b(), -1.0D);
    if (d > -1.0D)
      d = Math.min(400.0D, d); 
    return d;
  }
  
  public boolean e() {
    return this.e;
  }
  
  public String f() {
    return (this.a != null && this.a.toUpperCase().equals(this.a)) ? this.a.toLowerCase() : this.a;
  }
  
  public String toString() {
    return this.a;
  }
}
