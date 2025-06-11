package com.wisecoders.dbs.editors.csv.model.undo;

import com.wisecoders.dbs.editors.csv.fx.CsvEditor;
import com.wisecoders.dbs.editors.csv.model.CsvColumn;

public class CsvDeleteColumnChange extends CsvChange {
  CsvColumn b;
  
  public CsvDeleteColumnChange(CsvColumn paramCsvColumn) {
    this.b = paramCsvColumn;
  }
  
  public CsvChange a(CsvEditor paramCsvEditor) {
    this.b.a(true);
    paramCsvEditor.c.a.g();
    return this;
  }
  
  public CsvChange b(CsvEditor paramCsvEditor) {
    this.b.a(false);
    paramCsvEditor.c.a.g();
    return this;
  }
}
