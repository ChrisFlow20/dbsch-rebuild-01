package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.io.Serializable;

public class Transition implements Serializable, Cloneable, Comparable {
  static final long a = 40001L;
  
  char b;
  
  char c;
  
  State d;
  
  public Transition(char paramChar, State paramState) {
    this.b = this.c = paramChar;
    this.d = paramState;
  }
  
  public Transition(char paramChar1, char paramChar2, State paramState) {
    if (paramChar2 < paramChar1) {
      char c = paramChar2;
      paramChar2 = paramChar1;
      paramChar1 = c;
    } 
    this.b = paramChar1;
    this.c = paramChar2;
    this.d = paramState;
  }
  
  public char a() {
    return this.b;
  }
  
  public char b() {
    return this.c;
  }
  
  public State c() {
    return this.d;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof Transition) {
      Transition transition = (Transition)paramObject;
      return (transition.b == this.b && transition.c == this.c && transition.d == this.d);
    } 
    return false;
  }
  
  public int hashCode() {
    return this.b * 2 + this.c * 3;
  }
  
  public Transition d() {
    try {
      return (Transition)super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new RuntimeException(cloneNotSupportedException);
    } 
  }
  
  static void a(char paramChar, StringBuilder paramStringBuilder) {
    if (paramChar >= '!' && paramChar <= '~' && paramChar != '\\' && paramChar != '"') {
      paramStringBuilder.append(paramChar);
    } else {
      paramStringBuilder.append("\\u");
      String str = Integer.toHexString(paramChar);
      if (paramChar < '\020') {
        paramStringBuilder.append("000").append(str);
      } else if (paramChar < 'Ā') {
        paramStringBuilder.append("00").append(str);
      } else if (paramChar < 'က') {
        paramStringBuilder.append("0").append(str);
      } else {
        paramStringBuilder.append(str);
      } 
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    a(this.b, stringBuilder);
    if (this.b != this.c) {
      stringBuilder.append("-");
      a(this.c, stringBuilder);
    } 
    stringBuilder.append(" -> ").append(this.d.d);
    return stringBuilder.toString();
  }
  
  void a(StringBuilder paramStringBuilder) {
    paramStringBuilder.append(" -> ").append(this.d.d).append(" [label=\"");
    a(this.b, paramStringBuilder);
    if (this.b != this.c) {
      paramStringBuilder.append("-");
      a(this.c, paramStringBuilder);
    } 
    paramStringBuilder.append("\"]\n");
  }
  
  public int a(Transition paramTransition) {
    if (this.b < paramTransition.b)
      return -1; 
    if (this.b > paramTransition.b)
      return 1; 
    if (this.c < paramTransition.c)
      return -1; 
    if (this.c > paramTransition.c)
      return 1; 
    return this.d.b(paramTransition.d);
  }
}
