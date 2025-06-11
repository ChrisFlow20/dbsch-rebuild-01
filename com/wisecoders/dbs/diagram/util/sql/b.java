package com.wisecoders.dbs.diagram.util.sql;

class b {
  public LexerTokenType a;
  
  public String b;
  
  public int c;
  
  public int d;
  
  public int e;
  
  b a(LexerTokenType paramLexerTokenType, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    this.a = paramLexerTokenType;
    this.b = paramString;
    this.c = paramInt1;
    this.d = paramInt2;
    this.e = paramInt3;
    return this;
  }
  
  public String toString() {
    return "'" + this.b + "'\t\t\t[ " + String.valueOf(this.a) + " line:" + this.c + " start:" + this.d + " end:" + this.e + "]";
  }
}
