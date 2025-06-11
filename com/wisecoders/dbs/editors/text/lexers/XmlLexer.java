package com.wisecoders.dbs.editors.text.lexers;

import com.wisecoders.dbs.editors.text.DefaultLexer;
import com.wisecoders.dbs.editors.text.Token;
import com.wisecoders.dbs.editors.text.TokenType;
import java.io.IOException;
import java.io.Reader;

public final class XmlLexer extends DefaultLexer {
  public static final int d = -1;
  
  private static final int j = 16384;
  
  public static final int e = 0;
  
  public static final int f = 2;
  
  public static final int g = 4;
  
  public static final int h = 6;
  
  public static final int i = 8;
  
  private static final int[] k = new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 };
  
  private static final String l = "\t\000\001\001\001\003\001\033\001\033\001\002\022\000\001\001\001\005\001\025\001\031\002\000\001\027\001\026\005\000\001\006\001\n\001\023\n\013\001\b\001\030\001\004\001\024\001\007\001\f\001\000\001\020\001\b\001\016\001\017\001\b\016\b\001\021\006\b\001\r\001\000\001\022\001\000\001\b\001\000\032\t\n\000\001\0331\000\001\n\b\000\027\000\001\000\037\000\001\000Ȉ\000p\000\016\000\001\000ˡ\000\n\032\000\n\032Æ\000\n\032Ɯ\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032`\000\n\032v\000\n\032F\000\n\032Ė\000\n\032F\000\n\032f\000۠\000\n\032&\000\n\032Ĭ\000\n\032\000\n\032¦\000\n\032\006\000\n\032¶\000\n\032V\000\n\032\000\n\032\006\000\n\032Φ\000\f\000\002\000\032\000\001\033\001\033\027\000/\000Ġ\000ੰ\000ϰ\000\021\000瘟\000\n\032ʦ\000\n\032&\000\n\032Æ\000\n\032\026\000\n\032V\000\n\032Ɩ\000\n\032Ⰶ\000ࠀ\000က\000ऀ\000Ӑ\000 \000Ġ\000\n\032ä\000Ң\000\n\032஼\000\n\032\000\n\032<\000\n\032\000\n\032Ė\000\n\032ǖ\000\n\032Ŷ\000\n\032f\000\n\032Ȗ\000\n\032其\000\n\032æ\000\n\032汴\0002\032￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000⠏\000";
  
  private static final char[] m = a("\t\000\001\001\001\003\001\033\001\033\001\002\022\000\001\001\001\005\001\025\001\031\002\000\001\027\001\026\005\000\001\006\001\n\001\023\n\013\001\b\001\030\001\004\001\024\001\007\001\f\001\000\001\020\001\b\001\016\001\017\001\b\016\b\001\021\006\b\001\r\001\000\001\022\001\000\001\b\001\000\032\t\n\000\001\0331\000\001\n\b\000\027\000\001\000\037\000\001\000Ȉ\000p\000\016\000\001\000ˡ\000\n\032\000\n\032Æ\000\n\032Ɯ\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032v\000\n\032`\000\n\032v\000\n\032F\000\n\032Ė\000\n\032F\000\n\032f\000۠\000\n\032&\000\n\032Ĭ\000\n\032\000\n\032¦\000\n\032\006\000\n\032¶\000\n\032V\000\n\032\000\n\032\006\000\n\032Φ\000\f\000\002\000\032\000\001\033\001\033\027\000/\000Ġ\000ੰ\000ϰ\000\021\000瘟\000\n\032ʦ\000\n\032&\000\n\032Æ\000\n\032\026\000\n\032V\000\n\032Ɩ\000\n\032Ⰶ\000ࠀ\000က\000ऀ\000Ӑ\000 \000Ġ\000\n\032ä\000Ң\000\n\032஼\000\n\032\000\n\032<\000\n\032\000\n\032Ė\000\n\032ǖ\000\n\032Ŷ\000\n\032f\000\n\032Ȗ\000\n\032其\000\n\032æ\000\n\032汴\0002\032￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000￿\000⠏\000");
  
  private static final int[] n = k();
  
  private static final String o = "\005\000\004\001\001\000\002\001\001\000\001\001\001\002\005\001\001\000\001\003\t\000\001\004\001\005\001\000\001\006\001\000\001\007\002\000\001\b\001\000\001\t\002\000\001\n\001\000\001\013\001\f\002\000\001\r\001\016\004\000\001\017";
  
  private static int[] k() {
    int[] arrayOfInt = new int[57];
    int i = 0;
    i = a("\005\000\004\001\001\000\002\001\001\000\001\001\001\002\005\001\001\000\001\003\t\000\001\004\001\005\001\000\001\006\001\000\001\007\002\000\001\b\001\000\001\t\002\000\001\n\001\000\001\013\001\f\002\000\001\r\001\016\004\000\001\017", i, arrayOfInt);
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
  
  private static final int[] p = l();
  
  private static final String q = "\000\000\000\034\0008\000T\000p\000\000¨\000Ä\000à\000à\000ü\000Ę\000Ę\000Ĵ\000\000Ő\000Ŭ\000ƈ\000Ƥ\000ǀ\000ǜ\000Ǹ\000Ȕ\000Ȱ\000Ɍ\000ɨ\000ʄ\000ʠ\000ʼ\000˘\000Ő\000\000\000ƈ\000\000Ƥ\000\000˴\000̐\000̬\000͈\000\000ͤ\000΀\000\000Μ\000\000\000θ\000ϔ\000\000\000ϰ\000Ќ\000Ш\000ф\000";
  
  private static int[] l() {
    int[] arrayOfInt = new int[57];
    int i = 0;
    i = b("\000\000\000\034\0008\000T\000p\000\000¨\000Ä\000à\000à\000ü\000Ę\000Ę\000Ĵ\000\000Ő\000Ŭ\000ƈ\000Ƥ\000ǀ\000ǜ\000Ǹ\000Ȕ\000Ȱ\000Ɍ\000ɨ\000ʄ\000ʠ\000ʼ\000˘\000Ő\000\000\000ƈ\000\000Ƥ\000\000˴\000̐\000̬\000͈\000\000ͤ\000΀\000\000Μ\000\000\000θ\000ϔ\000\000\000ϰ\000Ќ\000Ш\000ф\000", i, arrayOfInt);
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
  
  private static final int[] r = m();
  
  private static final String s = "\002\006\001\000\001\006\001\007\022\006\001\b\003\006\001\000\002\t\001\n\003\t\001\013\024\t\001\n\002\f\001\r\017\f\001\016\b\f\001\r\002\006\001\000\004\006\001\017\002\020\004\006\004\020\001\006\001\021\001\006\001\022\001\023\004\006\001\000\002\006\001\000\005\006\002\020\002\006\001\024\001\006\004\020\003\006\001\022\001\023\004\006\"\000\001\025\002\000\002\026\002\000\001\027\001\000\004\026\001\000\001\030\021\000\001\031\017\000\001\032\002\000\006\n\001\033\033\n\001\034\025\n\022\r\001\035\033\r\001\036\t\r\006\000\001\037\001\000\004\037\002\000\004\037\002\000\001 \016\000\001!\024\000\002\"\002\000\021\"\001#\006\"\002$\002\000\022$\001#\005$\007\000\001%\032\000\001&\006\000\001'\024\000\001\026\001\000\004\026\002\000\004\026\022\000\002(\004\000\004(\022\000\002)\004\000\004)\023\000\001\031\016\000\001*\016\000\001+\016\000\001+\001\000\006\n\001,\033\n\001,\001-\024\n\022\r\001.\020\r\001/\n\r\001.\t\r\006\000\0010#\000\0011\023\000\001(\001\000\004(\002\000\004(\013\000\0032\002\000\001)\0013\004)\002\000\004)\025\000\001+\f\000\001*\001\000\001+\001\000\006\n\001,\0014\024\n\007\r\0014\n\r\001.\t\r\017\000\0015\r\000\0032\003\000\0013$\000\0016\034\000\0017\032\000\0018\030\000\0019\016\000";
  
  private static final int t = 0;
  
  private static final int u = 1;
  
  private static final int v = 2;
  
  private static int[] m() {
    int[] arrayOfInt = new int[1120];
    int i = 0;
    i = c("\002\006\001\000\001\006\001\007\022\006\001\b\003\006\001\000\002\t\001\n\003\t\001\013\024\t\001\n\002\f\001\r\017\f\001\016\b\f\001\r\002\006\001\000\004\006\001\017\002\020\004\006\004\020\001\006\001\021\001\006\001\022\001\023\004\006\001\000\002\006\001\000\005\006\002\020\002\006\001\024\001\006\004\020\003\006\001\022\001\023\004\006\"\000\001\025\002\000\002\026\002\000\001\027\001\000\004\026\001\000\001\030\021\000\001\031\017\000\001\032\002\000\006\n\001\033\033\n\001\034\025\n\022\r\001\035\033\r\001\036\t\r\006\000\001\037\001\000\004\037\002\000\004\037\002\000\001 \016\000\001!\024\000\002\"\002\000\021\"\001#\006\"\002$\002\000\022$\001#\005$\007\000\001%\032\000\001&\006\000\001'\024\000\001\026\001\000\004\026\002\000\004\026\022\000\002(\004\000\004(\022\000\002)\004\000\004)\023\000\001\031\016\000\001*\016\000\001+\016\000\001+\001\000\006\n\001,\033\n\001,\001-\024\n\022\r\001.\020\r\001/\n\r\001.\t\r\006\000\0010#\000\0011\023\000\001(\001\000\004(\002\000\004(\013\000\0032\002\000\001)\0013\004)\002\000\004)\025\000\001+\f\000\001*\001\000\001+\001\000\006\n\001,\0014\024\n\007\r\0014\n\r\001.\t\r\017\000\0015\r\000\0032\003\000\0013$\000\0016\034\000\0017\032\000\0018\030\000\0019\016\000", i, arrayOfInt);
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
  
  private static final String[] w = new String[] { "Unknown internal scanner error", "Error: could not match input", "Error: pushback value was too large" };
  
  private static final int[] x = n();
  
  private static final String y = "\005\000\001\t\003\001\001\000\002\001\001\000\001\001\001\t\005\001\001\000\001\001\t\000\002\t\001\000\001\t\001\000\001\t\002\000\001\001\001\000\001\t\002\000\001\t\001\000\002\t\002\000\002\t\004\000\001\t";
  
  private Reader z;
  
  private int A;
  
  private static int[] n() {
    int[] arrayOfInt = new int[57];
    int i = 0;
    i = d("\005\000\001\t\003\001\001\000\002\001\001\000\001\001\001\t\005\001\001\000\001\001\t\000\002\t\001\000\001\t\001\000\001\t\002\000\001\001\001\000\001\t\002\000\001\t\001\000\002\t\002\000\002\t\004\000\001\t", i, arrayOfInt);
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
  
  private int B = 0;
  
  private char[] C = new char[16384];
  
  private int D;
  
  private int E;
  
  private int F;
  
  private int G;
  
  private int H;
  
  private int I;
  
  private int J;
  
  private boolean K = true;
  
  private boolean L;
  
  private boolean M;
  
  private int N = 0;
  
  private static final byte O = 1;
  
  private static final byte P = -1;
  
  private static final byte Q = 2;
  
  private static final byte R = -2;
  
  private static final byte S = 3;
  
  private static final byte T = -3;
  
  private static final byte U = 4;
  
  private static final byte V = -4;
  
  public XmlLexer() {}
  
  public int e() {
    return this.I;
  }
  
  public XmlLexer(Reader paramReader) {
    this.z = paramReader;
  }
  
  private static char[] a(String paramString) {
    char[] arrayOfChar = new char[1114112];
    byte b1 = 0;
    byte b2 = 0;
    label10: while (b1 < 'Ÿ') {
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
    if (this.F > 0) {
      this.G += this.N;
      this.N = 0;
      System.arraycopy(this.C, this.F, this.C, 0, this.G - this.F);
      this.G -= this.F;
      this.E -= this.F;
      this.D -= this.F;
      this.F = 0;
    } 
    if (this.E >= this.C.length - this.N) {
      char[] arrayOfChar = new char[this.C.length * 2];
      System.arraycopy(this.C, 0, arrayOfChar, 0, this.C.length);
      this.C = arrayOfChar;
      this.G += this.N;
      this.N = 0;
    } 
    int i = this.C.length - this.G;
    int j = this.z.read(this.C, this.G, i);
    if (j == 0)
      throw new IOException("Reader returned 0 characters. See JFlex examples for workaround."); 
    if (j > 0) {
      this.G += j;
      if (j == i && 
        Character.isHighSurrogate(this.C[this.G - 1])) {
        this.G--;
        this.N = 1;
      } 
      return false;
    } 
    return true;
  }
  
  public final void j() {
    this.L = true;
    this.G = this.F;
    if (this.z != null)
      this.z.close(); 
  }
  
  public final void a(Reader paramReader) {
    this.z = paramReader;
    this.K = true;
    this.L = false;
    this.M = false;
    this.G = this.F = 0;
    this.E = this.D = 0;
    this.N = 0;
    this.H = this.I = this.J = 0;
    this.B = 0;
    if (this.C.length > 16384)
      this.C = new char[16384]; 
  }
  
  public final int a() {
    return this.B;
  }
  
  public final void b(int paramInt) {
    this.B = paramInt;
  }
  
  public final String d() {
    return new String(this.C, this.F, this.D - this.F);
  }
  
  public final char a(int paramInt) {
    return this.C[this.F + paramInt];
  }
  
  public final int c() {
    return this.D - this.F;
  }
  
  private void d(int paramInt) {
    String str;
    try {
      str = w[paramInt];
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      str = w[0];
    } 
    throw new Error(str);
  }
  
  public void c(int paramInt) {
    if (paramInt > c())
      d(2); 
    this.D -= paramInt;
  }
  
  public Token b() {
    int i = this.G;
    char[] arrayOfChar1 = this.C;
    char[] arrayOfChar2 = m;
    int[] arrayOfInt1 = r;
    int[] arrayOfInt2 = p;
    int[] arrayOfInt3 = x;
    while (true) {
      int j, n = this.D;
      this.I += n - this.F;
      int k = -1;
      int m = this.E = this.F = n;
      this.A = k[this.B];
      int i1 = arrayOfInt3[this.A];
      if ((i1 & 0x1) == 1)
        k = this.A; 
      while (true) {
        if (m < i) {
          j = Character.codePointAt(arrayOfChar1, m, i);
          m += Character.charCount(j);
        } else {
          if (this.L) {
            byte b = -1;
            break;
          } 
          this.E = m;
          this.D = n;
          boolean bool = o();
          m = this.E;
          n = this.D;
          arrayOfChar1 = this.C;
          i = this.G;
          if (bool) {
            byte b = -1;
            break;
          } 
          j = Character.codePointAt(arrayOfChar1, m, i);
          m += Character.charCount(j);
        } 
        int i2 = arrayOfInt1[arrayOfInt2[this.A] + arrayOfChar2[j]];
        if (i2 == -1)
          break; 
        this.A = i2;
        i1 = arrayOfInt3[this.A];
        if ((i1 & 0x1) == 1) {
          k = this.A;
          n = m;
          if ((i1 & 0x8) == 8)
            break; 
        } 
      } 
      this.D = n;
      if (j == -1 && this.F == this.E) {
        this.L = true;
        switch (this.B) {
          case 0:
            return null;
          case 58:
            continue;
          case 2:
            return null;
          case 59:
            continue;
          case 4:
            return null;
          case 60:
            continue;
          case 6:
            return null;
          case 61:
            continue;
          case 8:
            return null;
          case 62:
            continue;
        } 
        return null;
      } 
      switch ((k < 0) ? k : n[k]) {
        case 1:
        case 16:
          continue;
        case 2:
          b(0);
          return a(TokenType.m);
        case 17:
          continue;
        case 3:
          b(6);
          return a(TokenType.m, 1);
        case 18:
          continue;
        case 4:
          return a(TokenType.e);
        case 19:
          continue;
        case 5:
          b(0);
          return a(TokenType.m, -1);
        case 20:
          continue;
        case 6:
          return a(TokenType.g);
        case 21:
          continue;
        case 7:
          b(0);
          return a(TokenType.n, -2);
        case 22:
          continue;
        case 8:
          b(8);
          return a(TokenType.n, 2);
        case 23:
          continue;
        case 9:
          return a(TokenType.d);
        case 24:
          continue;
        case 10:
          b(0);
          return a(TokenType.j, -4);
        case 25:
          continue;
        case 11:
          b(0);
          return a(TokenType.j, -3);
        case 26:
          continue;
        case 12:
          b(2);
          return a(TokenType.j, 4);
        case 27:
          continue;
        case 13:
          return a(TokenType.m, -1);
        case 28:
          continue;
        case 14:
          c(3);
          return a(TokenType.i);
        case 29:
          continue;
        case 15:
          b(4);
          return a(TokenType.j, 3);
        case 30:
          continue;
      } 
      d(1);
    } 
  }
}
