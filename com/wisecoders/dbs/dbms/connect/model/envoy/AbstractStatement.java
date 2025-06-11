package com.wisecoders.dbs.dbms.connect.model.envoy;

public abstract class AbstractStatement implements AutoCloseable {
  public static final int a = 10;
  
  private boolean b = false;
  
  public abstract void a();
  
  public abstract String b();
  
  public void c() {
    this.b = true;
  }
  
  public boolean d() {
    return this.b;
  }
  
  public abstract void close();
}
