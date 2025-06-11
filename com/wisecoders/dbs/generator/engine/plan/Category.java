package com.wisecoders.dbs.generator.engine.plan;

import java.util.ArrayList;
import java.util.List;

public class Category {
  public String a;
  
  private final List b = new ArrayList();
  
  public Category(String paramString) {
    this.a = paramString;
  }
  
  public void a(String paramString) {
    this.a = paramString;
  }
  
  public DefinedPattern a(String paramString1, String paramString2) {
    DefinedPattern definedPattern = new DefinedPattern(this, paramString1, paramString2);
    this.b.add(definedPattern);
    return definedPattern;
  }
  
  public DefinedPattern b(String paramString) {
    for (DefinedPattern definedPattern : a()) {
      if (definedPattern.b().equalsIgnoreCase(paramString))
        return definedPattern; 
    } 
    return null;
  }
  
  public List a() {
    return this.b;
  }
  
  public String toString() {
    return this.a;
  }
}
