package com.wisecoders.dbs.generator.engine.plan;

import com.wisecoders.dbs.schema.Table;
import java.util.List;

public class MissingTableWarn extends Warn {
  public final List a;
  
  public MissingTableWarn(Table paramTable, List paramList) {
    super(paramTable);
    this.a = paramList;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("Has foreign keys referring table not included here : ");
    boolean bool = true;
    for (Table table : this.a) {
      stringBuilder.append(bool ? "" : ", ").append(table);
      bool = false;
    } 
    return stringBuilder.toString();
  }
}
