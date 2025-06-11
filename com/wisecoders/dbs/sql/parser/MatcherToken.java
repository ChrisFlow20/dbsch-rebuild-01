package com.wisecoders.dbs.sql.parser;

import com.wisecoders.dbs.editors.text.TokenType;

public class MatcherToken implements MatcherNode {
  public int a;
  
  public int b;
  
  public TokenType c;
  
  public String d;
  
  public final String e;
  
  private boolean f = false;
  
  public MatcherToken(String paramString1, int paramInt1, int paramInt2, TokenType paramTokenType, String paramString2) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramTokenType;
    this.d = paramString2;
    this.e = paramString1;
  }
  
  public String c() {
    return this.e;
  }
  
  public String d() {
    return DDLParserUtil.a(this.e);
  }
  
  public TokenType b() {
    return this.c;
  }
  
  public String toString() {
    return this.d + this.d;
  }
  
  public String e() {
    return this.e;
  }
  
  public String a() {
    return String.valueOf(this.c);
  }
  
  public void a(boolean paramBoolean) {
    this.f = paramBoolean;
  }
  
  public void f() {
    this.f = false;
  }
  
  public boolean g() {
    return this.f;
  }
}
