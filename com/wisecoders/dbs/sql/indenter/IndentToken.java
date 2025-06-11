package com.wisecoders.dbs.sql.indenter;

public class IndentToken {
  public final String a;
  
  public final int b;
  
  public boolean c;
  
  public boolean d = false;
  
  public boolean e = false;
  
  public int f = 0;
  
  public IndentToken(String paramString, int paramInt) {
    this(paramString, paramInt, false);
  }
  
  public IndentToken(String paramString, int paramInt, boolean paramBoolean) {
    this.a = paramString;
    this.b = paramInt;
    this.c = paramBoolean;
  }
  
  public IndentToken a(boolean paramBoolean) {
    this.e = paramBoolean;
    return this;
  }
  
  public boolean a(String... paramVarArgs) {
    for (String str : paramVarArgs) {
      if (str.equalsIgnoreCase(this.a))
        return true; 
    } 
    return false;
  }
  
  public boolean b(String... paramVarArgs) {
    for (String str : paramVarArgs) {
      if (this.a.endsWith(str))
        return true; 
    } 
    return false;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.c) {
      stringBuilder.append("\n");
      stringBuilder.append("   ".repeat(Math.max(0, this.f + (this.d ? 1 : 0))));
    } else if (this.e) {
      stringBuilder.append(" ");
    } 
    stringBuilder.append(this.a);
    return stringBuilder.toString();
  }
  
  public String a() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.c)
      stringBuilder.append("NewLine:"); 
    if (this.d)
      stringBuilder.append("AddLevel:"); 
    stringBuilder.append(this.f).append(":");
    stringBuilder.append(this.a.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r"));
    return stringBuilder.toString();
  }
}
