package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Set;

public class RunAutomaton implements Serializable {
  static final long a = 20001L;
  
  int b;
  
  boolean[] c;
  
  int d;
  
  int[] e;
  
  char[] f;
  
  int[] g;
  
  final void a() {
    this.g = new int[65536];
    byte b1 = 0;
    for (byte b2 = 0; b2 <= '￿'; b2++) {
      if (b1 + 1 < this.f.length && b2 == this.f[b1 + 1])
        b1++; 
      this.g[b2] = b1;
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("initial state: ").append(this.d).append("\n");
    for (byte b = 0; b < this.b; b++) {
      stringBuilder.append("state " + b);
      if (this.c[b]) {
        stringBuilder.append(" [accept]:\n");
      } else {
        stringBuilder.append(" [reject]:\n");
      } 
      for (byte b1 = 0; b1 < this.f.length; b1++) {
        int i = this.e[b * this.f.length + b1];
        if (i != -1) {
          char c1;
          char c = this.f[b1];
          if (b1 + 1 < this.f.length) {
            c1 = (char)(this.f[b1 + 1] - 1);
          } else {
            c1 = '￿';
          } 
          stringBuilder.append(" ");
          Transition.a(c, stringBuilder);
          if (c != c1) {
            stringBuilder.append("-");
            Transition.a(c1, stringBuilder);
          } 
          stringBuilder.append(" -> ").append(i).append("\n");
        } 
      } 
    } 
    return stringBuilder.toString();
  }
  
  public int b() {
    return this.b;
  }
  
  public boolean a(int paramInt) {
    return this.c[paramInt];
  }
  
  public int c() {
    return this.d;
  }
  
  public char[] d() {
    return (char[])this.f.clone();
  }
  
  int a(char paramChar) {
    return SpecialOperations.a(paramChar, this.f);
  }
  
  private RunAutomaton() {}
  
  public RunAutomaton(Automaton paramAutomaton) {
    this(paramAutomaton, true);
  }
  
  public static RunAutomaton a(URL paramURL) {
    return a(paramURL.openStream());
  }
  
  public static RunAutomaton a(InputStream paramInputStream) {
    ObjectInputStream objectInputStream = new ObjectInputStream(paramInputStream);
    return (RunAutomaton)objectInputStream.readObject();
  }
  
  public void a(OutputStream paramOutputStream) {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(paramOutputStream);
    objectOutputStream.writeObject(this);
    objectOutputStream.flush();
  }
  
  public RunAutomaton(Automaton paramAutomaton, boolean paramBoolean) {
    paramAutomaton.H();
    this.f = paramAutomaton.n();
    Set set = paramAutomaton.i();
    Automaton.a(set);
    this.d = paramAutomaton.f.d;
    this.b = set.size();
    this.c = new boolean[this.b];
    this.e = new int[this.b * this.f.length];
    for (byte b = 0; b < this.b * this.f.length; b++)
      this.e[b] = -1; 
    for (State state : set) {
      int i = state.d;
      this.c[i] = state.b;
      for (byte b1 = 0; b1 < this.f.length; b1++) {
        State state1 = state.a(this.f[b1]);
        if (state1 != null)
          this.e[i * this.f.length + b1] = state1.d; 
      } 
    } 
    if (paramBoolean)
      a(); 
  }
  
  public int a(int paramInt, char paramChar) {
    if (this.g == null)
      return this.e[paramInt * this.f.length + a(paramChar)]; 
    return this.e[paramInt * this.f.length + this.g[paramChar - 0]];
  }
  
  public boolean a(String paramString) {
    int i = this.d;
    int j = paramString.length();
    for (byte b = 0; b < j; b++) {
      i = a(i, paramString.charAt(b));
      if (i == -1)
        return false; 
    } 
    return this.c[i];
  }
  
  public int a(String paramString, int paramInt) {
    int i = this.d;
    int j = paramString.length();
    byte b = -1;
    for (byte b1 = 0; paramInt <= j; paramInt++, b1++) {
      if (this.c[i])
        b = b1; 
      if (paramInt == j)
        break; 
      i = a(i, paramString.charAt(paramInt));
      if (i == -1)
        break; 
    } 
    return b;
  }
  
  public AutomatonMatcher a(CharSequence paramCharSequence) {
    return new AutomatonMatcher(paramCharSequence, this);
  }
  
  public AutomatonMatcher a(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
    return new AutomatonMatcher(paramCharSequence.subSequence(paramInt1, paramInt2), this);
  }
}
