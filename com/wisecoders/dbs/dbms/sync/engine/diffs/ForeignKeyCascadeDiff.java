package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ForeignKeyCascadeDiff extends AbstractDiff {
  public ForeignKeyCascadeDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    ForeignKey foreignKey1 = (ForeignKey)this.pair.getUnit(paramSyncSide);
    ForeignKey foreignKey2 = (ForeignKey)this.pair.getUnit(paramSyncSide.opposite());
    StringBuilder stringBuilder = new StringBuilder();
    if (foreignKey1.getDeleteAction() != foreignKey2.getDeleteAction())
      stringBuilder.append("ON DELETE ").append(foreignKey1.getDeleteAction()).append(" "); 
    if (foreignKey1.getUpdateAction() != foreignKey2.getUpdateAction())
      stringBuilder.append("ON UPDATE ").append(foreignKey1.getUpdateAction()); 
    return stringBuilder.toString();
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    ForeignKey foreignKey1 = (ForeignKey)this.pair.getUnit(paramSyncSide.opposite()), foreignKey2 = (ForeignKey)this.pair.getUnit(paramSyncSide);
    foreignKey2.setDeleteAction(foreignKey1.getDeleteAction());
    foreignKey2.setUpdateAction(foreignKey1.getUpdateAction());
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Dbms dbms = Dbms.get(paramString);
    ForeignKey foreignKey1 = (ForeignKey)this.pair.getUnit(paramSyncSide.opposite()), foreignKey2 = (ForeignKey)this.pair.getUnit(paramSyncSide);
    if (foreignKey2 != null && !SyncUtil.c(foreignKey2))
      alterScript.addAll(dbms.alterForeignKey.a(this.pair, foreignKey2)); 
    if (foreignKey1 != null && !SyncUtil.c(foreignKey1))
      alterScript.addAll(dbms.alterForeignKey.b(this.pair, foreignKey1)); 
    return alterScript;
  }
}
