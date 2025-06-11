package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.Arrays;
import java.util.Collection;

class e {
  e a;
  
  State[] b;
  
  State c;
  
  char d;
  
  int e;
  
  boolean f;
  
  boolean g;
  
  int h;
  
  private e() {}
  
  e(Collection paramCollection, Automaton paramAutomaton) {
    this.b = new State[paramCollection.size()];
    byte b = 0;
    for (Automaton automaton : paramCollection)
      this.b[b++] = automaton.f(); 
    this.c = paramAutomaton.f();
    a();
  }
  
  e(e parame, int paramInt, State paramState, char paramChar) {
    this.a = parame;
    this.b = (State[])parame.b.clone();
    this.c = parame.c;
    this.b[paramInt] = paramState;
    this.d = paramChar;
    a();
  }
  
  e(e parame, int paramInt, State paramState1, State paramState2, char paramChar) {
    this.a = parame;
    this.b = (State[])parame.b.clone();
    this.c = parame.c;
    this.b[paramInt] = paramState1;
    this.c = paramState2;
    this.d = paramChar;
    if (!this.g) {
      this.f = parame.f;
      this.h = parame.h;
    } 
    a();
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof e) {
      e e1 = (e)paramObject;
      return (this.f == e1.f && this.g == e1.g && this.h == e1.h && 

        
        Arrays.equals((Object[])this.b, (Object[])e1.b) && this.c == e1.c);
    } 
    return false;
  }
  
  public int hashCode() {
    return this.e;
  }
  
  private void a() {
    this.e = 0;
    for (byte b = 0; b < this.b.length; b++)
      this.e ^= this.b[b].hashCode(); 
    this.e ^= this.c.hashCode() * 100;
    if (this.f || this.g)
      this.e += this.h; 
  }
}
