package com.wisecoders.dbs.generator.engine.generators.automaton;

public class StatePair {
  State a;
  
  State b;
  
  State c;
  
  StatePair(State paramState1, State paramState2, State paramState3) {
    this.a = paramState1;
    this.b = paramState2;
    this.c = paramState3;
  }
  
  public StatePair(State paramState1, State paramState2) {
    this.b = paramState1;
    this.c = paramState2;
  }
  
  public State a() {
    return this.b;
  }
  
  public State b() {
    return this.c;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof StatePair) {
      StatePair statePair = (StatePair)paramObject;
      return (statePair.b == this.b && statePair.c == this.c);
    } 
    return false;
  }
  
  public int hashCode() {
    return this.b.hashCode() + this.c.hashCode();
  }
}
