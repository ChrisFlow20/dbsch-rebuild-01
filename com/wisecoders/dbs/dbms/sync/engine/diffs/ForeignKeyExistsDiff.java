package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ForeignKeyExistsDiff extends AbstractExistsDiff {
  public ForeignKeyExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public boolean targetEntityExistsInLeftSide() {
    return (this.pair.right == null || (this.pair.leftParent != null && (((Table)this.pair.leftParent).getEntity().getSchema()).project.getSimilarEntity(((ForeignKey)this.pair.right).getTargetEntity()) != null));
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {}
  
  public void mergeFinal(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    ForeignKey foreignKey = (ForeignKey)this.pair.getUnit(paramSyncSide.opposite());
    if (foreignKey != null) {
      Table table = (Table)this.pair.getParent(paramSyncSide);
      a(table.getSchema(), foreignKey);
    } 
    super.mergeFinal(paramSyncSide, paramGenericLayoutPane);
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    ForeignKey foreignKey1 = (ForeignKey)this.pair.getUnit(paramSyncSide.opposite()), foreignKey2 = (ForeignKey)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (foreignKey2 != null && !SyncUtil.c(foreignKey2)) {
      alterScript.addAll(dbms.alterForeignKey.a(this.pair, foreignKey2));
      if (foreignKey2.getComment() != null && dbms.alterForeignKey.e())
        alterScript.add(dbms.alterForeignKey.b(this.pair, null, foreignKey2)); 
    } 
    if (foreignKey1 != null && !SyncUtil.c(foreignKey1))
      alterScript.addAll(dbms.alterForeignKey.b(this.pair, foreignKey1)); 
    return alterScript;
  }
}
