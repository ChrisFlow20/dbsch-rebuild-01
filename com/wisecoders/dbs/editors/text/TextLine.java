package com.wisecoders.dbs.editors.text;

public class TextLine {
  public static final byte a = 1;
  
  public static final byte b = 2;
  
  public static final byte c = 4;
  
  private String e = "";
  
  byte d = 0;
  
  public TextLine(String paramString) {
    this.e = paramString;
  }
  
  public void a(String paramString) {
    this.e = paramString;
  }
  
  public void a(int paramInt, String paramString) {
    if (paramInt < this.e.length()) {
      this.e = this.e.substring(0, paramInt) + this.e.substring(0, paramInt) + paramString;
    } else {
      this.e += this.e;
    } 
  }
  
  public void a(int paramInt) {
    a(paramInt, 1);
  }
  
  public void a(int paramInt1, int paramInt2) {
    int i = this.e.length();
    if (i > 0)
      if (paramInt1 < i - paramInt2) {
        this.e = this.e.substring(0, paramInt1) + this.e.substring(0, paramInt1);
      } else if (paramInt1 == i - paramInt2) {
        this.e = this.e.substring(0, paramInt1 - paramInt2 + 1);
      }  
  }
  
  public boolean a() {
    return this.e.trim().isEmpty();
  }
  
  public String b() {
    return this.e;
  }
  
  String b(int paramInt) {
    return this.e.substring(0, paramInt);
  }
  
  String c(int paramInt) {
    return this.e.substring(Math.min(paramInt, this.e.length()));
  }
  
  public int c() {
    return this.e.length();
  }
  
  public char d(int paramInt) {
    return this.e.charAt(paramInt);
  }
  
  public int e(int paramInt) {
    return this.e.codePointAt(paramInt);
  }
  
  public String b(int paramInt1, int paramInt2) {
    return this.e.substring(paramInt1, Math.min(paramInt2, this.e.length()));
  }
  
  public String f(int paramInt) {
    return this.e.substring(paramInt);
  }
  
  int g(int paramInt) {
    boolean bool1 = false, bool2 = false;
    while (paramInt < c()) {
      char c = d(paramInt);
      boolean bool3 = Character.isWhitespace(c);
      boolean bool4 = Character.isLetterOrDigit(c);
      if ((!bool3 && bool1) || (!bool4 && bool2))
        return paramInt; 
      if (bool3)
        bool1 = true; 
      if (bool4)
        bool2 = true; 
      paramInt++;
    } 
    return paramInt;
  }
  
  int h(int paramInt) {
    boolean bool1 = false, bool2 = false;
    while (paramInt > 0) {
      paramInt--;
      char c = d(paramInt);
      boolean bool3 = Character.isWhitespace(c);
      boolean bool4 = Character.isLetterOrDigit(c);
      if ((!bool3 && bool1) || (!bool4 && bool2))
        return paramInt + 1; 
      if (bool3)
        bool1 = true; 
      if (bool4)
        bool2 = true; 
    } 
    return paramInt;
  }
  
  public String toString() {
    return this.e;
  }
  
  public void a(boolean paramBoolean) {
    if (paramBoolean) {
      this.d = (byte)(this.d | 0x1);
    } else {
      this.d = (byte)(this.d & 0xFFFFFFFE);
    } 
  }
  
  public void b(boolean paramBoolean) {
    if (paramBoolean) {
      this.d = (byte)(this.d | 0x4);
    } else {
      this.d = (byte)(this.d & 0xFFFFFFFB);
    } 
  }
  
  public void c(boolean paramBoolean) {
    if (paramBoolean) {
      this.d = (byte)(this.d | 0x2);
    } else {
      this.d = (byte)(this.d & 0xFFFFFFFD);
    } 
  }
  
  public boolean d() {
    return ((this.d & 0x1) > 0);
  }
  
  public boolean e() {
    return ((this.d & 0x4) > 0);
  }
  
  public boolean f() {
    return ((this.d & 0x2) > 0);
  }
}
