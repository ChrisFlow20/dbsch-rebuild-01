package com.wisecoders.dbs.schema.store;

import com.wisecoders.dbs.schema.Column;

final class a extends Record {
  private final Column a;
  
  private final String b;
  
  private a(Column paramColumn, String paramString) {
    this.a = paramColumn;
    this.b = paramString;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lcom/wisecoders/dbs/schema/store/a;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #631	-> 0
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/schema/store/a;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #631	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/schema/store/a;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #631	-> 0
  }
  
  public Column a() {
    return this.a;
  }
  
  public String b() {
    return this.b;
  }
}
