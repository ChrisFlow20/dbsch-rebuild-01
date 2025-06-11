package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.schema.Column;

public class Access extends Dbms {
  public Access() {
    super("Access");
  }
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff && (paramAbstractDiff.pair.getUnit() instanceof com.wisecoders.dbs.schema.Index || paramAbstractDiff.pair.getUnit() instanceof com.wisecoders.dbs.schema.ForeignKey));
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if ("boolean".equalsIgnoreCase(paramString1))
      paramString1 = "YESNO"; 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
}
