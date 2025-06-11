package com.wisecoders.dbs.loader.fx;

import org.apache.commons.csv.CSVFormat;

final class a extends Record {
  private final String a;
  
  private final CSVFormat b;
  
  private a(String paramString, CSVFormat paramCSVFormat) {
    this.a = paramString;
    this.b = paramCSVFormat;
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/loader/fx/a;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #563	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/loader/fx/a;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #563	-> 0
  }
  
  public String a() {
    return this.a;
  }
  
  public CSVFormat b() {
    return this.b;
  }
  
  public String toString() {
    return this.a;
  }
}
