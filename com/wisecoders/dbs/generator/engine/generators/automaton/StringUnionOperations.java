package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;

public final class StringUnionOperations {
  static {
    a = new StringUnionOperations$1();
  }
  
  private HashMap c = new LinkedHashMap<>();
  
  private f d = new f();
  
  public static final Comparator a;
  
  private StringBuilder e;
  
  public void a(CharSequence paramCharSequence) {
    if (!b && this.c == null)
      throw new AssertionError("Automaton already built."); 
    if (!b && paramCharSequence.length() <= 0)
      throw new AssertionError("Input sequences must not be empty."); 
    if (!b && this.e != null && a.compare(this.e, paramCharSequence) > 0)
      throw new AssertionError("Input must be sorted: " + String.valueOf(this.e) + " >= " + String.valueOf(paramCharSequence)); 
    if (!b && !b(paramCharSequence))
      throw new AssertionError(); 
    byte b = 0;
    int i = paramCharSequence.length();
    f f2 = this.d;
    f f1;
    while (b < i && (f1 = f2.c(paramCharSequence.charAt(b))) != null) {
      f2 = f1;
      b++;
    } 
    if (f2.c())
      a(f2); 
    a(f2, paramCharSequence, b);
  }
  
  public f a() {
    if (this.c == null)
      throw new IllegalStateException(); 
    if (this.d.c())
      a(this.d); 
    this.c = null;
    return this.d;
  }
  
  private static State a(f paramf, IdentityHashMap<f, State> paramIdentityHashMap) {
    State state = (State)paramIdentityHashMap.get(paramf);
    if (state != null)
      return state; 
    state = new State();
    state.a(paramf.c);
    paramIdentityHashMap.put(paramf, state);
    byte b = 0;
    char[] arrayOfChar = paramf.a;
    for (f f1 : paramf.b)
      state.a(new Transition(arrayOfChar[b++], a(f1, paramIdentityHashMap))); 
    return state;
  }
  
  public static State a(CharSequence[] paramArrayOfCharSequence) {
    StringUnionOperations stringUnionOperations = new StringUnionOperations();
    for (CharSequence charSequence : paramArrayOfCharSequence)
      stringUnionOperations.a(charSequence); 
    return a(stringUnionOperations.a(), new IdentityHashMap<>());
  }
  
  private boolean b(CharSequence paramCharSequence) {
    if (this.e == null)
      this.e = new StringBuilder(); 
    this.e.setLength(0);
    this.e.append(paramCharSequence);
    return true;
  }
  
  private void a(f paramf) {
    f f1 = paramf.e();
    if (f1.c())
      a(f1); 
    f f2 = (f)this.c.get(f1);
    if (f2 != null) {
      paramf.a(f2);
    } else {
      this.c.put(f1, f1);
    } 
  }
  
  private void a(f paramf, CharSequence paramCharSequence, int paramInt) {
    int i = paramCharSequence.length();
    for (int j = paramInt; j < i; j++)
      paramf = paramf.b(paramCharSequence.charAt(j)); 
    paramf.c = true;
  }
}
