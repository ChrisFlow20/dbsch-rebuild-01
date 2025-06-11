package com.wisecoders.dbs.dbms.driver.model;

import java.sql.Driver;

public final class DriverJarClass extends Record {
  private final String a;
  
  private final DriverJar b;
  
  private final int c;
  
  private final int d;
  
  private final Driver e;
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/dbms/driver/model/DriverJarClass;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #9	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/dbms/driver/model/DriverJarClass;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #9	-> 0
  }
  
  public String a() {
    return this.a;
  }
  
  public DriverJar b() {
    return this.b;
  }
  
  public int c() {
    return this.c;
  }
  
  public int d() {
    return this.d;
  }
  
  public Driver e() {
    return this.e;
  }
  
  public DriverJarClass(String paramString, DriverJar paramDriverJar, int paramInt1, int paramInt2, Driver paramDriver) {
    this.a = paramString;
    this.b = paramDriverJar;
    this.c = paramInt1;
    this.d = paramInt2;
    this.e = paramDriver;
  }
  
  public String toString() {
    return this.b.toString();
  }
}
