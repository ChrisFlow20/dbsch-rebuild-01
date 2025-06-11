package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class State implements Serializable, Comparable {
  static final long a = 30001L;
  
  boolean b;
  
  Set c;
  
  int d;
  
  int e;
  
  static int f;
  
  public State() {
    a();
    this.e = f++;
  }
  
  final void a() {
    this.c = new TreeSet();
  }
  
  public Set b() {
    return this.c;
  }
  
  public void a(Transition paramTransition) {
    this.c.add(paramTransition);
  }
  
  public void a(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public boolean c() {
    return this.b;
  }
  
  public State a(char paramChar) {
    for (Transition transition : this.c) {
      if (transition.b <= paramChar && paramChar <= transition.c)
        return transition.d; 
    } 
    return null;
  }
  
  public void a(char paramChar, Collection<State> paramCollection) {
    for (Transition transition : this.c) {
      if (transition.b <= paramChar && paramChar <= transition.c)
        paramCollection.add(transition.d); 
    } 
  }
  
  void a(State paramState) {
    if (paramState.b)
      this.b = true; 
    for (Transition transition : paramState.c)
      this.c.add(transition); 
  }
  
  Transition[] b(boolean paramBoolean) {
    Transition[] arrayOfTransition = (Transition[])this.c.toArray((Object[])new Transition[this.c.size()]);
    Arrays.sort(arrayOfTransition, new g(paramBoolean));
    return arrayOfTransition;
  }
  
  public List c(boolean paramBoolean) {
    return Arrays.asList(b(paramBoolean));
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("state ").append(this.d);
    if (this.b) {
      stringBuilder.append(" [accept]");
    } else {
      stringBuilder.append(" [reject]");
    } 
    stringBuilder.append(":\n");
    for (Transition transition : this.c)
      stringBuilder.append("  ").append(transition.toString()).append("\n"); 
    return stringBuilder.toString();
  }
  
  public int b(State paramState) {
    return paramState.e - this.e;
  }
  
  public boolean equals(Object paramObject) {
    return super.equals(paramObject);
  }
  
  public int hashCode() {
    return super.hashCode();
  }
}
