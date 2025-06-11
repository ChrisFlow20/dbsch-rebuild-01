package com.wisecoders.dbs.editors.text.lexers;

import com.wisecoders.dbs.editors.text.DefaultLexer;
import com.wisecoders.dbs.editors.text.Token;
import com.wisecoders.dbs.editors.text.TokenType;
import java.io.IOException;
import java.io.Reader;

public final class PropertiesLexer extends DefaultLexer {
  public static final int d = -1;
  
  private static final int f = 16384;
  
  public static final int e = 0;
  
  private static final int[] g = new int[] { 0, 0 };
  
  private static final String h = "\t\000\001\002\001\004\001\b\001\b\001\003\022\000\001\006\002\000\001\001\n\000\001\005\001\000\n\005\003\000\001\007\003\000\032\005\004\000\001\005\001\000\032\005\n\000\001\bᾢ\000\001\b\001\b￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000?\000";
  
  private static final char[] i = a("\t\000\001\002\001\004\001\b\001\b\001\003\022\000\001\006\002\000\001\001\n\000\001\005\001\000\n\005\003\000\001\007\003\000\032\005\004\000\001\005\001\000\032\005\n\000\001\bᾢ\000\001\b\001\b￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000?\000");
  
  private static final int[] j = k();
  
  private static final String k = "\001\000\001\001\001\002\002\001\002\002\002\000\001\003";
  
  private static int[] k() {
    int[] arrayOfInt = new int[10];
    int i = 0;
    i = a("\001\000\001\001\001\002\002\001\002\002\002\000\001\003", i, arrayOfInt);
    return arrayOfInt;
  }
  
  private static int a(String paramString, int paramInt, int[] paramArrayOfint) {
    byte b = 0;
    int i = paramInt;
    int j = paramString.length();
    label10: while (b < j) {
      char c1 = paramString.charAt(b++);
      char c2 = paramString.charAt(b++);
      while (true) {
        paramArrayOfint[i++] = c2;
        if (--c1 <= '\000')
          continue label10; 
      } 
    } 
    return i;
  }
  
  private static final int[] l = l();
  
  private static final String m = "\000\000\000\t\000\022\000\033\000$\000-\000\t\0006\000$\000\t";
  
  private static int[] l() {
    int[] arrayOfInt = new int[10];
    int i = 0;
    i = b("\000\000\000\t\000\022\000\033\000$\000-\000\t\0006\000$\000\t", i, arrayOfInt);
    return arrayOfInt;
  }
  
  private static int b(String paramString, int paramInt, int[] paramArrayOfint) {
    byte b = 0;
    int i = paramInt;
    int j = paramString.length();
    while (b < j) {
      int k = paramString.charAt(b++) << 16;
      paramArrayOfint[i++] = k | paramString.charAt(b++);
    } 
    return i;
  }
  
  private static final int[] n = m();
  
  private static final String o = "\001\002\001\003\001\002\001\004\001\002\002\005\001\002\n\000\003\003\001\006\001\007\004\003\004\000\001\002\006\000\001\b\002\000\002\t\001\n\005\000\001\007\006\000\001\b\003\000\001\b\001\n\001\000";
  
  private static final int p = 0;
  
  private static final int q = 1;
  
  private static final int r = 2;
  
  private static int[] m() {
    int[] arrayOfInt = new int[63];
    int i = 0;
    i = c("\001\002\001\003\001\002\001\004\001\002\002\005\001\002\n\000\003\003\001\006\001\007\004\003\004\000\001\002\006\000\001\b\002\000\002\t\001\n\005\000\001\007\006\000\001\b\003\000\001\b\001\n\001\000", i, arrayOfInt);
    return arrayOfInt;
  }
  
  private static int c(String paramString, int paramInt, int[] paramArrayOfint) {
    byte b = 0;
    int i = paramInt;
    int j = paramString.length();
    label10: while (b < j) {
      char c1 = paramString.charAt(b++);
      char c2 = paramString.charAt(b++);
      c2--;
      while (true) {
        paramArrayOfint[i++] = c2;
        if (--c1 <= '\000')
          continue label10; 
      } 
    } 
    return i;
  }
  
  private static final String[] s = new String[] { "Unknown internal scanner error", "Error: could not match input", "Error: pushback value was too large" };
  
  private static final int[] t = n();
  
  private static final String u = "\001\000\001\t\004\001\001\t\002\000\001\t";
  
  private Reader v;
  
  private int w;
  
  private static int[] n() {
    int[] arrayOfInt = new int[10];
    int i = 0;
    i = d("\001\000\001\t\004\001\001\t\002\000\001\t", i, arrayOfInt);
    return arrayOfInt;
  }
  
  private static int d(String paramString, int paramInt, int[] paramArrayOfint) {
    byte b = 0;
    int i = paramInt;
    int j = paramString.length();
    label10: while (b < j) {
      char c1 = paramString.charAt(b++);
      char c2 = paramString.charAt(b++);
      while (true) {
        paramArrayOfint[i++] = c2;
        if (--c1 <= '\000')
          continue label10; 
      } 
    } 
    return i;
  }
  
  private int x = 0;
  
  private char[] y = new char[16384];
  
  private int z;
  
  private int A;
  
  private int B;
  
  private int C;
  
  private int D;
  
  private int E;
  
  private int F;
  
  private boolean G = true;
  
  private boolean H;
  
  private boolean I;
  
  private int J = 0;
  
  public PropertiesLexer() {}
  
  public int e() {
    return this.E;
  }
  
  public PropertiesLexer(Reader paramReader) {
    this.v = paramReader;
  }
  
  private static char[] a(String paramString) {
    char[] arrayOfChar = new char[1114112];
    byte b1 = 0;
    byte b2 = 0;
    label10: while (b1 < 88) {
      char c1 = paramString.charAt(b1++);
      char c2 = paramString.charAt(b1++);
      while (true) {
        arrayOfChar[b2++] = c2;
        if (--c1 <= '\000')
          continue label10; 
      } 
    } 
    return arrayOfChar;
  }
  
  private boolean o() {
    if (this.B > 0) {
      this.C += this.J;
      this.J = 0;
      System.arraycopy(this.y, this.B, this.y, 0, this.C - this.B);
      this.C -= this.B;
      this.A -= this.B;
      this.z -= this.B;
      this.B = 0;
    } 
    if (this.A >= this.y.length - this.J) {
      char[] arrayOfChar = new char[this.y.length * 2];
      System.arraycopy(this.y, 0, arrayOfChar, 0, this.y.length);
      this.y = arrayOfChar;
      this.C += this.J;
      this.J = 0;
    } 
    int i = this.y.length - this.C;
    int j = this.v.read(this.y, this.C, i);
    if (j == 0)
      throw new IOException("Reader returned 0 characters. See JFlex examples for workaround."); 
    if (j > 0) {
      this.C += j;
      if (j == i && 
        Character.isHighSurrogate(this.y[this.C - 1])) {
        this.C--;
        this.J = 1;
      } 
      return false;
    } 
    return true;
  }
  
  public final void j() {
    this.H = true;
    this.C = this.B;
    if (this.v != null)
      this.v.close(); 
  }
  
  public final void a(Reader paramReader) {
    this.v = paramReader;
    this.G = true;
    this.H = false;
    this.I = false;
    this.C = this.B = 0;
    this.A = this.z = 0;
    this.J = 0;
    this.D = this.E = this.F = 0;
    this.x = 0;
    if (this.y.length > 16384)
      this.y = new char[16384]; 
  }
  
  public final int a() {
    return this.x;
  }
  
  public final void b(int paramInt) {
    this.x = paramInt;
  }
  
  public final String d() {
    return new String(this.y, this.B, this.z - this.B);
  }
  
  public final char a(int paramInt) {
    return this.y[this.B + paramInt];
  }
  
  public final int c() {
    return this.z - this.B;
  }
  
  private void d(int paramInt) {
    String str;
    try {
      str = s[paramInt];
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      str = s[0];
    } 
    throw new Error(str);
  }
  
  public void c(int paramInt) {
    if (paramInt > c())
      d(2); 
    this.z -= paramInt;
  }
  
  public Token b() {
    int i = this.C;
    char[] arrayOfChar1 = this.y;
    char[] arrayOfChar2 = i;
    int[] arrayOfInt1 = n;
    int[] arrayOfInt2 = l;
    int[] arrayOfInt3 = t;
    while (true) {
      int j, n = this.z;
      this.E += n - this.B;
      int k = -1;
      int m = this.A = this.B = n;
      this.w = g[this.x];
      int i1 = arrayOfInt3[this.w];
      if ((i1 & 0x1) == 1)
        k = this.w; 
      while (true) {
        if (m < i) {
          j = Character.codePointAt(arrayOfChar1, m, i);
          m += Character.charCount(j);
        } else {
          if (this.H) {
            byte b = -1;
            break;
          } 
          this.A = m;
          this.z = n;
          boolean bool = o();
          m = this.A;
          n = this.z;
          arrayOfChar1 = this.y;
          i = this.C;
          if (bool) {
            byte b = -1;
            break;
          } 
          j = Character.codePointAt(arrayOfChar1, m, i);
          m += Character.charCount(j);
        } 
        int i2 = arrayOfInt1[arrayOfInt2[this.w] + arrayOfChar2[j]];
        if (i2 == -1)
          break; 
        this.w = i2;
        i1 = arrayOfInt3[this.w];
        if ((i1 & 0x1) == 1) {
          k = this.w;
          n = m;
          if ((i1 & 0x8) == 8)
            break; 
        } 
      } 
      this.z = n;
      if (j == -1 && this.B == this.A) {
        this.H = true;
        return null;
      } 
      switch ((k < 0) ? k : j[k]) {
        case 1:
        case 4:
          continue;
        case 2:
          return a(TokenType.i);
        case 5:
          continue;
        case 3:
          return a(TokenType.c);
        case 6:
          continue;
      } 
      d(1);
    } 
  }
}
