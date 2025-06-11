package com.wisecoders.dbs.data.model.result;

public final class ResultPosition extends Record {
  private final int a;
  
  private final ResultColumn b;
  
  public ResultPosition(int paramInt, ResultColumn paramResultColumn) {
    this.a = paramInt;
    this.b = paramResultColumn;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lcom/wisecoders/dbs/data/model/result/ResultPosition;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #3	-> 0
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/data/model/result/ResultPosition;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #3	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/data/model/result/ResultPosition;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #3	-> 0
  }
  
  public int a() {
    return this.a;
  }
  
  public ResultColumn b() {
    return this.b;
  }
}
