package com.wisecoders.dbs.editors.csv.model.undo;

import com.wisecoders.dbs.editors.csv.fx.CsvEditor;
import com.wisecoders.dbs.editors.csv.model.CsvColumn;

public class CsvEditChange extends CsvChange {
  private final int b;
  
  private final CsvColumn c;
  
  private final String d;
  
  private String e;
  
  public CsvEditChange(int paramInt, CsvColumn paramCsvColumn, String paramString) {
    this.b = paramInt;
    this.c = paramCsvColumn;
    this.d = paramString;
  }
  
  public CsvChange a(CsvEditor paramCsvEditor) {
    this.e = paramCsvEditor.b.b(this.c, this.b);
    paramCsvEditor.b.a(this.b, this.c, this.d);
    return this;
  }
  
  public CsvChange b(CsvEditor paramCsvEditor) {
    paramCsvEditor.b.a(this.b, this.c, this.e);
    paramCsvEditor.c.a.a(paramCsvEditor.b.g(this.b), this.c);
    return this;
  }
}
