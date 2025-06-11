package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ColumnDefaultValueDiff extends AbstractDiff {
  public ColumnDefaultValueDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    return "Default : " + ((Column)this.pair.getUnit(paramSyncSide)).getDefaultValue();
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Column column1 = (Column)this.pair.getUnit(paramSyncSide.opposite()), column2 = (Column)this.pair.getUnit(paramSyncSide);
    column2.setDefaultValue(column1.getDefaultValue());
  }
  
  public void mergeFinal(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {}
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Column column = (Column)this.pair.getUnit(paramSyncSide);
    String str = column.getDefaultValue();
    Dbms dbms = Dbms.get(paramString);
    if (!SyncUtil.c(column))
      if (str == null) {
        alterScript.addAll(dbms.alterColumn.c(this.pair, column));
      } else {
        alterScript.addAll(dbms.alterColumn.b(this.pair, column));
      }  
    return alterScript;
  }
}
