package com.wisecoders.dbs.editors.csv.model.undo;

import com.wisecoders.dbs.editors.csv.fx.CsvEditor;
import com.wisecoders.dbs.editors.csv.fx.CsvEditorTableColumn;
import com.wisecoders.dbs.editors.csv.model.CsvColumn;

public class CsvInsertColumnChange extends CsvChange {
  private final int b;
  
  private final CsvColumn c;
  
  public CsvInsertColumnChange(int paramInt, CsvColumn paramCsvColumn) {
    this.c = paramCsvColumn;
    this.b = paramInt;
  }
  
  public CsvChange a(CsvEditor paramCsvEditor) {
    this.c.a(false);
    paramCsvEditor.c.a.getColumns().add(this.b, new CsvEditorTableColumn(paramCsvEditor, this.c));
    paramCsvEditor.c.a.g();
    return this;
  }
  
  public CsvChange b(CsvEditor paramCsvEditor) {
    this.c.a(true);
    paramCsvEditor.c.a.g();
    return this;
  }
}
