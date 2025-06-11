package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.regex.MatchResult;

public class AutomatonMatcher implements MatchResult {
  private final RunAutomaton a;
  
  private final CharSequence b;
  
  private int c;
  
  private int d;
  
  AutomatonMatcher(CharSequence paramCharSequence, RunAutomaton paramRunAutomaton) {
    this.c = -1;
    this.d = -1;
    this.b = paramCharSequence;
    this.a = paramRunAutomaton;
  }
  
  public boolean a() {
    int i, j, k;
    switch (c()) {
      case -2:
        return false;
      case -1:
        i = 0;
        break;
      default:
        i = d();
        i++;
        if (i == c() && i > e().length()) {
          a(-2, -2);
          return false;
        } 
        break;
    } 
    if (this.a.a(this.a.c())) {
      j = i;
      k = i;
    } else {
      j = -1;
      k = -1;
    } 
    int m = e().length();
    while (i < m) {
      int n = this.a.c();
      for (int i1 = i; i1 < m; i1++) {
        int i2 = this.a.a(n, e().charAt(i1));
        if (i2 == -1)
          break; 
        if (this.a.a(i2)) {
          j = i;
          k = i1 + 1;
        } 
        n = i2;
      } 
      if (j != -1) {
        a(j, k);
        return true;
      } 
      i++;
    } 
    if (j != -1) {
      a(j, k);
      return true;
    } 
    a(-2, -2);
    return false;
  }
  
  private void a(int paramInt1, int paramInt2) {
    if (paramInt1 > paramInt2)
      throw new IllegalArgumentException("Start must be less than or equal to end: " + paramInt1 + ", " + paramInt2); 
    this.c = paramInt1;
    this.d = paramInt2;
  }
  
  private int c() {
    return this.c;
  }
  
  private int d() {
    return this.d;
  }
  
  private CharSequence e() {
    return this.b;
  }
  
  public int end() {
    f();
    return this.d;
  }
  
  public int end(int paramInt) {
    a(paramInt);
    return end();
  }
  
  public String group() {
    f();
    return this.b.subSequence(this.c, this.d).toString();
  }
  
  public String group(int paramInt) {
    a(paramInt);
    return group();
  }
  
  public int groupCount() {
    return 0;
  }
  
  public int start() {
    f();
    return this.c;
  }
  
  public int start(int paramInt) {
    a(paramInt);
    return start();
  }
  
  public MatchResult b() {
    AutomatonMatcher automatonMatcher = new AutomatonMatcher(this.b, this.a);
    automatonMatcher.c = this.c;
    automatonMatcher.d = this.d;
    return automatonMatcher;
  }
  
  private static void a(int paramInt) {
    if (paramInt != 0)
      throw new IndexOutOfBoundsException("The only group supported is 0."); 
  }
  
  private void f() {
    if (this.c < 0 || this.d < 0)
      throw new IllegalStateException("There was no available match."); 
  }
}
