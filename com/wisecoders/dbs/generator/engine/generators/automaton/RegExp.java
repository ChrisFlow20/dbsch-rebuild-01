package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class RegExp {
  public static final int a = 1;
  
  public static final int b = 2;
  
  public static final int c = 4;
  
  public static final int d = 8;
  
  public static final int e = 16;
  
  public static final int f = 32;
  
  public static final int g = 65535;
  
  public static final int h = 0;
  
  private static boolean v = false;
  
  d i;
  
  RegExp j;
  
  RegExp k;
  
  String l;
  
  char m;
  
  int n;
  
  int o;
  
  int p;
  
  char q;
  
  char r;
  
  String s;
  
  int t;
  
  int u;
  
  RegExp() {}
  
  public RegExp(String paramString) {
    this(paramString, 65535);
  }
  
  public RegExp(String paramString, int paramInt) {
    RegExp regExp;
    this.s = paramString;
    this.t = paramInt;
    if (paramString.length() == 0) {
      regExp = a("");
    } else {
      regExp = f();
      if (this.u < this.s.length())
        throw new IllegalArgumentException("end-of-string expected at position " + this.u); 
    } 
    this.i = regExp.i;
    this.j = regExp.j;
    this.k = regExp.k;
    this.l = regExp.l;
    this.m = regExp.m;
    this.n = regExp.n;
    this.o = regExp.o;
    this.p = regExp.p;
    this.q = regExp.q;
    this.r = regExp.r;
    this.s = null;
  }
  
  public Automaton a() {
    return a((Map)null, (AutomatonProvider)null, true);
  }
  
  public Automaton a(boolean paramBoolean) {
    return a((Map)null, (AutomatonProvider)null, paramBoolean);
  }
  
  public Automaton a(AutomatonProvider paramAutomatonProvider) {
    return a((Map)null, paramAutomatonProvider, true);
  }
  
  public Automaton a(AutomatonProvider paramAutomatonProvider, boolean paramBoolean) {
    return a((Map)null, paramAutomatonProvider, paramBoolean);
  }
  
  public Automaton a(Map paramMap) {
    return a(paramMap, (AutomatonProvider)null, true);
  }
  
  public Automaton a(Map paramMap, boolean paramBoolean) {
    return a(paramMap, (AutomatonProvider)null, paramBoolean);
  }
  
  public boolean b(boolean paramBoolean) {
    boolean bool = v;
    v = paramBoolean;
    return bool;
  }
  
  private Automaton a(Map paramMap, AutomatonProvider paramAutomatonProvider, boolean paramBoolean) {
    boolean bool = false;
    if (v)
      bool = Automaton.b(true); 
    Automaton automaton = b(paramMap, paramAutomatonProvider, paramBoolean);
    if (v)
      Automaton.b(bool); 
    return automaton;
  }
  
  private Automaton b(Map paramMap, AutomatonProvider paramAutomatonProvider, boolean paramBoolean) {
    ArrayList arrayList;
    Automaton automaton2, automaton1 = null;
    switch (RegExp$1.a[this.i.ordinal()]) {
      case 1:
        arrayList = new ArrayList();
        a(this.j, d.a, arrayList, paramMap, paramAutomatonProvider, paramBoolean);
        a(this.k, d.a, arrayList, paramMap, paramAutomatonProvider, paramBoolean);
        automaton1 = BasicOperations.a(arrayList);
        automaton1.L();
        break;
      case 2:
        arrayList = new ArrayList();
        a(this.j, d.b, arrayList, paramMap, paramAutomatonProvider, paramBoolean);
        a(this.k, d.b, arrayList, paramMap, paramAutomatonProvider, paramBoolean);
        automaton1 = BasicOperations.a(arrayList);
        automaton1.L();
        break;
      case 3:
        automaton1 = this.j.b(paramMap, paramAutomatonProvider, paramBoolean).c(this.k.b(paramMap, paramAutomatonProvider, paramBoolean));
        automaton1.L();
        break;
      case 4:
        automaton1 = this.j.b(paramMap, paramAutomatonProvider, paramBoolean).E();
        automaton1.L();
        break;
      case 5:
        automaton1 = this.j.b(paramMap, paramAutomatonProvider, paramBoolean).F();
        automaton1.L();
        break;
      case 6:
        automaton1 = this.j.b(paramMap, paramAutomatonProvider, paramBoolean).d(this.n);
        automaton1.L();
        break;
      case 7:
        automaton1 = this.j.b(paramMap, paramAutomatonProvider, paramBoolean).a(this.n, this.o);
        automaton1.L();
        break;
      case 8:
        automaton1 = this.j.b(paramMap, paramAutomatonProvider, paramBoolean).G();
        automaton1.L();
        break;
      case 9:
        automaton1 = BasicAutomata.a(this.m);
        break;
      case 10:
        automaton1 = BasicAutomata.a(this.q, this.r);
        break;
      case 11:
        automaton1 = BasicAutomata.d();
        break;
      case 12:
        automaton1 = BasicAutomata.a();
        break;
      case 13:
        automaton1 = BasicAutomata.b(this.l);
        break;
      case 14:
        automaton1 = BasicAutomata.c();
        break;
      case 15:
        automaton2 = null;
        if (paramMap != null)
          automaton2 = (Automaton)paramMap.get(this.l); 
        if (automaton2 == null && paramAutomatonProvider != null)
          try {
            automaton2 = paramAutomatonProvider.a(this.l);
          } catch (IOException iOException) {
            throw new IllegalArgumentException(iOException);
          }  
        if (automaton2 == null)
          throw new IllegalArgumentException("'" + this.l + "' not found"); 
        automaton1 = automaton2.y();
        break;
      case 16:
        automaton1 = BasicAutomata.a(this.n, this.o, this.p);
        break;
    } 
    return automaton1;
  }
  
  private void a(RegExp paramRegExp, d paramd, List<Automaton> paramList, Map paramMap, AutomatonProvider paramAutomatonProvider, boolean paramBoolean) {
    if (paramRegExp.i == paramd) {
      a(paramRegExp.j, paramd, paramList, paramMap, paramAutomatonProvider, paramBoolean);
      a(paramRegExp.k, paramd, paramList, paramMap, paramAutomatonProvider, paramBoolean);
    } else {
      paramList.add(paramRegExp.b(paramMap, paramAutomatonProvider, paramBoolean));
    } 
  }
  
  public String toString() {
    return a(new StringBuilder()).toString();
  }
  
  StringBuilder a(StringBuilder paramStringBuilder) {
    String str1;
    String str2;
    switch (RegExp$1.a[this.i.ordinal()]) {
      case 1:
        paramStringBuilder.append("(");
        this.j.a(paramStringBuilder);
        paramStringBuilder.append("|");
        this.k.a(paramStringBuilder);
        paramStringBuilder.append(")");
        break;
      case 2:
        this.j.a(paramStringBuilder);
        this.k.a(paramStringBuilder);
        break;
      case 3:
        paramStringBuilder.append("(");
        this.j.a(paramStringBuilder);
        paramStringBuilder.append("&");
        this.k.a(paramStringBuilder);
        paramStringBuilder.append(")");
        break;
      case 4:
        paramStringBuilder.append("(");
        this.j.a(paramStringBuilder);
        paramStringBuilder.append(")?");
        break;
      case 5:
        paramStringBuilder.append("(");
        this.j.a(paramStringBuilder);
        paramStringBuilder.append(")*");
        break;
      case 6:
        paramStringBuilder.append("(");
        this.j.a(paramStringBuilder);
        paramStringBuilder.append("){").append(this.n).append(",}");
        break;
      case 7:
        paramStringBuilder.append("(");
        this.j.a(paramStringBuilder);
        paramStringBuilder.append("){").append(this.n).append(",").append(this.o).append("}");
        break;
      case 8:
        paramStringBuilder.append("~(");
        this.j.a(paramStringBuilder);
        paramStringBuilder.append(")");
        break;
      case 9:
        paramStringBuilder.append("\\").append(this.m);
        break;
      case 10:
        paramStringBuilder.append("[\\").append(this.q).append("-\\").append(this.r).append("]");
        break;
      case 11:
        paramStringBuilder.append(".");
        break;
      case 12:
        paramStringBuilder.append("#");
        break;
      case 13:
        paramStringBuilder.append("\"").append(this.l).append("\"");
        break;
      case 14:
        paramStringBuilder.append("@");
        break;
      case 15:
        paramStringBuilder.append("<").append(this.l).append(">");
        break;
      case 16:
        str1 = Integer.toString(this.n);
        str2 = Integer.toString(this.o);
        paramStringBuilder.append("<");
        if (this.p > 0)
          for (int i = str1.length(); i < this.p; i++)
            paramStringBuilder.append('0');  
        paramStringBuilder.append(str1).append("-");
        if (this.p > 0)
          for (int i = str2.length(); i < this.p; i++)
            paramStringBuilder.append('0');  
        paramStringBuilder.append(str2).append(">");
        break;
    } 
    return paramStringBuilder;
  }
  
  public Set b() {
    TreeSet treeSet = new TreeSet();
    a(treeSet);
    return treeSet;
  }
  
  void a(Set<String> paramSet) {
    switch (RegExp$1.a[this.i.ordinal()]) {
      case 1:
      case 2:
      case 3:
        this.j.a(paramSet);
        this.k.a(paramSet);
        break;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
        this.j.a(paramSet);
        break;
      case 15:
        paramSet.add(this.l);
        break;
    } 
  }
  
  static RegExp a(RegExp paramRegExp1, RegExp paramRegExp2) {
    RegExp regExp = new RegExp();
    regExp.i = d.a;
    regExp.j = paramRegExp1;
    regExp.k = paramRegExp2;
    return regExp;
  }
  
  static RegExp b(RegExp paramRegExp1, RegExp paramRegExp2) {
    if ((paramRegExp1.i == d.i || paramRegExp1.i == d.m) && (paramRegExp2.i == d.i || paramRegExp2.i == d.m))
      return d(paramRegExp1, paramRegExp2); 
    RegExp regExp = new RegExp();
    regExp.i = d.b;
    if (paramRegExp1.i == d.b && (paramRegExp1.k.i == d.i || paramRegExp1.k.i == d.m) && (paramRegExp2.i == d.i || paramRegExp2.i == d.m)) {
      regExp.j = paramRegExp1.j;
      regExp.k = d(paramRegExp1.k, paramRegExp2);
    } else if ((paramRegExp1.i == d.i || paramRegExp1.i == d.m) && paramRegExp2.i == d.b && (paramRegExp2.j.i == d.i || paramRegExp2.j.i == d.m)) {
      regExp.j = d(paramRegExp1, paramRegExp2.j);
      regExp.k = paramRegExp2.k;
    } else {
      regExp.j = paramRegExp1;
      regExp.k = paramRegExp2;
    } 
    return regExp;
  }
  
  private static RegExp d(RegExp paramRegExp1, RegExp paramRegExp2) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramRegExp1.i == d.m) {
      stringBuilder.append(paramRegExp1.l);
    } else {
      stringBuilder.append(paramRegExp1.m);
    } 
    if (paramRegExp2.i == d.m) {
      stringBuilder.append(paramRegExp2.l);
    } else {
      stringBuilder.append(paramRegExp2.m);
    } 
    return a(stringBuilder.toString());
  }
  
  static RegExp c(RegExp paramRegExp1, RegExp paramRegExp2) {
    RegExp regExp = new RegExp();
    regExp.i = d.c;
    regExp.j = paramRegExp1;
    regExp.k = paramRegExp2;
    return regExp;
  }
  
  static RegExp a(RegExp paramRegExp) {
    RegExp regExp = new RegExp();
    regExp.i = d.d;
    regExp.j = paramRegExp;
    return regExp;
  }
  
  static RegExp b(RegExp paramRegExp) {
    RegExp regExp = new RegExp();
    regExp.i = d.e;
    regExp.j = paramRegExp;
    return regExp;
  }
  
  static RegExp a(RegExp paramRegExp, int paramInt) {
    RegExp regExp = new RegExp();
    regExp.i = d.f;
    regExp.j = paramRegExp;
    regExp.n = paramInt;
    return regExp;
  }
  
  static RegExp a(RegExp paramRegExp, int paramInt1, int paramInt2) {
    RegExp regExp = new RegExp();
    regExp.i = d.g;
    regExp.j = paramRegExp;
    regExp.n = paramInt1;
    regExp.o = paramInt2;
    return regExp;
  }
  
  static RegExp c(RegExp paramRegExp) {
    RegExp regExp = new RegExp();
    regExp.i = d.h;
    regExp.j = paramRegExp;
    return regExp;
  }
  
  static RegExp a(char paramChar) {
    RegExp regExp = new RegExp();
    regExp.i = d.i;
    regExp.m = paramChar;
    return regExp;
  }
  
  static RegExp a(char paramChar1, char paramChar2) {
    RegExp regExp = new RegExp();
    regExp.i = d.j;
    regExp.q = paramChar1;
    regExp.r = paramChar2;
    return regExp;
  }
  
  static RegExp c() {
    RegExp regExp = new RegExp();
    regExp.i = d.k;
    return regExp;
  }
  
  static RegExp d() {
    RegExp regExp = new RegExp();
    regExp.i = d.l;
    return regExp;
  }
  
  static RegExp a(String paramString) {
    RegExp regExp = new RegExp();
    regExp.i = d.m;
    regExp.l = paramString;
    return regExp;
  }
  
  static RegExp e() {
    RegExp regExp = new RegExp();
    regExp.i = d.n;
    return regExp;
  }
  
  static RegExp b(String paramString) {
    RegExp regExp = new RegExp();
    regExp.i = d.o;
    regExp.l = paramString;
    return regExp;
  }
  
  static RegExp a(int paramInt1, int paramInt2, int paramInt3) {
    RegExp regExp = new RegExp();
    regExp.i = d.p;
    regExp.n = paramInt1;
    regExp.o = paramInt2;
    regExp.p = paramInt3;
    return regExp;
  }
  
  private boolean c(String paramString) {
    return (p() && paramString.indexOf(this.s.charAt(this.u)) != -1);
  }
  
  private boolean b(char paramChar) {
    if (this.u >= this.s.length())
      return false; 
    if (this.s.charAt(this.u) == paramChar) {
      this.u++;
      return true;
    } 
    return false;
  }
  
  private boolean p() {
    return (this.u < this.s.length());
  }
  
  private char q() {
    if (!p())
      throw new IllegalArgumentException("unexpected end-of-string"); 
    return this.s.charAt(this.u++);
  }
  
  private boolean a(int paramInt) {
    return ((this.t & paramInt) != 0);
  }
  
  final RegExp f() {
    RegExp regExp = g();
    if (b('|'))
      regExp = a(regExp, f()); 
    return regExp;
  }
  
  final RegExp g() {
    RegExp regExp = h();
    if (a(1) && b('&'))
      regExp = c(regExp, g()); 
    return regExp;
  }
  
  final RegExp h() {
    RegExp regExp = i();
    if (p() && !c(")|") && (!a(1) || !c("&")))
      regExp = b(regExp, h()); 
    return regExp;
  }
  
  final RegExp i() {
    RegExp regExp = j();
    while (c("?*+{")) {
      if (b('?')) {
        regExp = a(regExp);
        continue;
      } 
      if (b('*')) {
        regExp = b(regExp);
        continue;
      } 
      if (b('+')) {
        regExp = a(regExp, 1);
        continue;
      } 
      if (b('{')) {
        int i = this.u;
        while (c("0123456789"))
          q(); 
        if (i == this.u)
          throw new IllegalArgumentException("integer expected at position " + this.u); 
        int j = Integer.parseInt(this.s.substring(i, this.u));
        int k = -1;
        if (b(',')) {
          i = this.u;
          while (c("0123456789"))
            q(); 
          if (i != this.u)
            k = Integer.parseInt(this.s.substring(i, this.u)); 
        } else {
          k = j;
        } 
        if (!b('}'))
          throw new IllegalArgumentException("expected '}' at position " + this.u); 
        if (k == -1) {
          regExp = a(regExp, j);
          continue;
        } 
        regExp = a(regExp, j, k);
      } 
    } 
    return regExp;
  }
  
  final RegExp j() {
    if (a(2) && b('~'))
      return c(j()); 
    return k();
  }
  
  final RegExp k() {
    if (b('[')) {
      boolean bool = false;
      if (b('^'))
        bool = true; 
      RegExp regExp = l();
      if (bool)
        regExp = c(c(), c(regExp)); 
      if (!b(']'))
        throw new IllegalArgumentException("expected ']' at position " + this.u); 
      return regExp;
    } 
    return n();
  }
  
  final RegExp l() {
    RegExp regExp = m();
    while (p() && !c("]"))
      regExp = a(regExp, m()); 
    return regExp;
  }
  
  final RegExp m() {
    char c = o();
    if (b('-')) {
      if (c("]"))
        return a(a(c), a('-')); 
      return a(c, o());
    } 
    return a(c);
  }
  
  final RegExp n() {
    if (b('.'))
      return c(); 
    if (a(4) && b('#'))
      return d(); 
    if (a(8) && b('@'))
      return e(); 
    if (b('"')) {
      int i = this.u;
      while (p() && !c("\""))
        q(); 
      if (!b('"'))
        throw new IllegalArgumentException("expected '\"' at position " + this.u); 
      return a(this.s.substring(i, this.u - 1));
    } 
    if (b('(')) {
      if (b(')'))
        return a(""); 
      RegExp regExp = f();
      if (!b(')'))
        throw new IllegalArgumentException("expected ')' at position " + this.u); 
      return regExp;
    } 
    if ((a(16) || a(32)) && b('<')) {
      int i = this.u;
      while (p() && !c(">"))
        q(); 
      if (!b('>'))
        throw new IllegalArgumentException("expected '>' at position " + this.u); 
      String str = this.s.substring(i, this.u - 1);
      int j = str.indexOf('-');
      if (j == -1) {
        if (!a(16))
          throw new IllegalArgumentException("interval syntax error at position " + this.u - 1); 
        return b(str);
      } 
      if (!a(32))
        throw new IllegalArgumentException("illegal identifier at position " + this.u - 1); 
      try {
        boolean bool;
        if (j == 0 || j == str.length() - 1 || j != str.lastIndexOf('-'))
          throw new NumberFormatException(); 
        String str1 = str.substring(0, j);
        String str2 = str.substring(j + 1, str.length());
        int k = Integer.parseInt(str1);
        int m = Integer.parseInt(str2);
        if (str1.length() == str2.length()) {
          bool = str1.length();
        } else {
          bool = false;
        } 
        if (k > m) {
          int n = k;
          k = m;
          m = n;
        } 
        return a(k, m, bool);
      } catch (NumberFormatException numberFormatException) {
        throw new IllegalArgumentException("interval syntax error at position " + this.u - 1);
      } 
    } 
    return a(o());
  }
  
  final char o() {
    b('\\');
    return q();
  }
}
