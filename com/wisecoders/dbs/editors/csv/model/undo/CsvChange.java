package com.wisecoders.dbs.editors.csv.model.undo;

import com.wisecoders.dbs.editors.csv.fx.CsvEditor;

public abstract class CsvChange {
  public final long a = System.currentTimeMillis();
  
  public abstract CsvChange a(CsvEditor paramCsvEditor);
  
  public abstract CsvChange b(CsvEditor paramCsvEditor);
}
