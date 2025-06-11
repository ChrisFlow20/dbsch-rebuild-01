package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class IndexUniquenessDiff extends AbstractDiff {
  public IndexUniquenessDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    Index index = (Index)this.pair.getUnit(paramSyncSide);
    return index.getType().toString();
  }
  
  public String getOperationString(SyncSide paramSyncSide) {
    return "Type";
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Index index1 = (Index)this.pair.getUnit(paramSyncSide.opposite()), index2 = (Index)this.pair.getUnit(paramSyncSide);
    index2.setType(index1.getType());
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Index index1 = (Index)this.pair.getUnit(paramSyncSide.opposite()), index2 = (Index)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (index2 != null && !SyncUtil.c(index2))
      alterScript.addAll(dbms.getAlterIndex(index2.getType()).a(this.pair, index2)); 
    if (index1 != null && !SyncUtil.c(index1))
      alterScript.addAll(dbms.getAlterIndex(index1.getType()).c(this.pair, index1)); 
    return alterScript;
  }
}
