package com.wisecoders.dbs.generator.engine.plan;

import com.wisecoders.dbs.diagram.fx.ToolTipFactory;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Table;

public class CyclicFkWarn extends Warn {
  public final ForeignKey a;
  
  public CyclicFkWarn(Table paramTable, ForeignKey paramForeignKey) {
    super(paramTable);
    this.a = paramForeignKey;
  }
  
  public String toString() {
    return "Cyclic Foreign Key " + ToolTipFactory.b(this.a);
  }
}
