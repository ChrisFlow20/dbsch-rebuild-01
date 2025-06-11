package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.io.Serializable;
import java.util.Comparator;

class g implements Serializable, Comparator {
  static final long a = 10001L;
  
  boolean b;
  
  g(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public int a(Transition paramTransition1, Transition paramTransition2) {
    if (this.b && 
      paramTransition1.d != paramTransition2.d) {
      if (paramTransition1.d == null)
        return -1; 
      if (paramTransition2.d == null)
        return 1; 
      if (paramTransition1.d.d < paramTransition2.d.d)
        return -1; 
      if (paramTransition1.d.d > paramTransition2.d.d)
        return 1; 
    } 
    if (paramTransition1.b < paramTransition2.b)
      return -1; 
    if (paramTransition1.b > paramTransition2.b)
      return 1; 
    if (paramTransition1.c > paramTransition2.c)
      return -1; 
    if (paramTransition1.c < paramTransition2.c)
      return 1; 
    if (!this.b && 
      paramTransition1.d != paramTransition2.d) {
      if (paramTransition1.d == null)
        return -1; 
      if (paramTransition2.d == null)
        return 1; 
      if (paramTransition1.d.d < paramTransition2.d.d)
        return -1; 
      if (paramTransition1.d.d > paramTransition2.d.d)
        return 1; 
    } 
    return 0;
  }
}
