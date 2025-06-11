package com.wisecoders.dbs.editors.csv.model.undo;

import com.wisecoders.dbs.editors.csv.fx.CsvEditor;

public class CsvInsertRowChange extends CsvChange {
  private final int b;
  
  public CsvInsertRowChange(int paramInt) {
    this.b = paramInt;
  }
  
  public CsvChange a(CsvEditor paramCsvEditor) {
    paramCsvEditor.b.e(this.b);
    paramCsvEditor.c.a.refresh();
    return this;
  }
  
  public CsvChange b(CsvEditor paramCsvEditor) {
    paramCsvEditor.b.d(paramCsvEditor.b.f(this.b));
    paramCsvEditor.c.a.refresh();
    return this;
  }
}
