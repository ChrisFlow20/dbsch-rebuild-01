package com.wisecoders.dbs.browse.store;

import com.wisecoders.dbs.browse.fx.FxBrowseTableView;
import com.wisecoders.dbs.browse.model.BrowseResult;
import com.wisecoders.dbs.browse.model.Cascade;

final class a extends Record {
  private final FxBrowseTableView a;
  
  private final BrowseResult b;
  
  private final Cascade c;
  
  private a(FxBrowseTableView paramFxBrowseTableView, BrowseResult paramBrowseResult, Cascade paramCascade) {
    this.a = paramFxBrowseTableView;
    this.b = paramBrowseResult;
    this.c = paramCascade;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lcom/wisecoders/dbs/browse/store/a;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #95	-> 0
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/browse/store/a;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #95	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/browse/store/a;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #95	-> 0
  }
  
  public FxBrowseTableView a() {
    return this.a;
  }
  
  public BrowseResult b() {
    return this.b;
  }
  
  public Cascade c() {
    return this.c;
  }
}
