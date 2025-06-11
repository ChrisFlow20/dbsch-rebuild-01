package com.wisecoders.dbs.editors.csv.model.undo;

import com.wisecoders.dbs.editors.csv.fx.CsvEditor;
import com.wisecoders.dbs.editors.csv.model.CsvModel;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class CsvDeleteRowsChange extends CsvChange {
  int[] b;
  
  private final LinkedHashMap c = new LinkedHashMap<>();
  
  public CsvDeleteRowsChange(CsvModel paramCsvModel, List paramList) {
    this.b = new int[paramList.size()];
    byte b = 0;
    for (Iterator<Integer> iterator = paramList.iterator(); iterator.hasNext(); ) {
      int i = ((Integer)iterator.next()).intValue();
      this.b[b] = paramCsvModel.g(i);
      this.c.put(Integer.valueOf(i), (byte[])paramCsvModel.c.get(i));
      b++;
    } 
  }
  
  public CsvChange a(CsvEditor paramCsvEditor) {
    for (Integer integer : this.c.keySet())
      paramCsvEditor.b.d(integer.intValue()); 
    paramCsvEditor.c.a.refresh();
    return this;
  }
  
  public CsvChange b(CsvEditor paramCsvEditor) {
    byte b = 0;
    for (Integer integer : this.c.keySet()) {
      paramCsvEditor.b.a(this.b[b], integer.intValue(), (byte[])this.c.get(integer));
      b++;
    } 
    paramCsvEditor.c.a.refresh();
    return this;
  }
}
