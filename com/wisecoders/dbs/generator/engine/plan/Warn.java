package com.wisecoders.dbs.generator.engine.plan;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.schema.Column;

public class Warn {
  protected final String b;
  
  public final Entity c;
  
  public final Column d;
  
  private int a = 1;
  
  public Warn(Entity paramEntity) {
    this(paramEntity, null, "");
  }
  
  public Warn(Entity paramEntity, Column paramColumn, String paramString) {
    this.b = paramString;
    this.c = paramEntity;
    this.d = paramColumn;
  }
  
  public void a() {
    this.a++;
  }
  
  public int b() {
    return this.a;
  }
  
  public boolean a(String paramString) {
    boolean bool = this.b.equals(paramString);
    if (bool)
      a(); 
    return bool;
  }
  
  public String toString() {
    return this.b;
  }
}
