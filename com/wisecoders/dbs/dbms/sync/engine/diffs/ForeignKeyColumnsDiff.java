package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.Iterator;

@DoNotObfuscate
public class ForeignKeyColumnsDiff extends AbstractDiff {
  public ForeignKeyColumnsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    ForeignKey foreignKey = (ForeignKey)this.pair.getUnit(paramSyncSide);
    return ForeignKey.listAttributes(foreignKey.getSourceAttributes(), " references ", foreignKey.getTargetAttributes());
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    ForeignKey foreignKey1 = (ForeignKey)this.pair.getUnit(paramSyncSide), foreignKey2 = (ForeignKey)this.pair.getUnit(paramSyncSide.opposite());
    foreignKey1.setDeleteAction(foreignKey2.getDeleteAction());
    foreignKey1.setUpdateAction(foreignKey2.getUpdateAction());
    foreignKey1.setComment(foreignKey2.getComment());
    foreignKey1.setCommentTags(foreignKey2.getCommentTags());
    foreignKey1.clearAllAttributes();
    for (Iterator<Column> iterator1 = foreignKey2.getSourceAttributes().iterator(), iterator2 = foreignKey2.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
      Column column1 = iterator1.next();
      Column column2 = iterator2.next();
      Column column3 = (column1 != null) ? (Column)foreignKey1.getEntity().getAttributes().getByName(column1.getName()) : null;
      Column column4 = (column2 != null) ? (Column)foreignKey2.getEntity().getAttributes().getByName(column2.getName()) : null;
      if (column3 != null && (column4 != null || foreignKey1.is(UnitProperty.f).booleanValue()))
        foreignKey1.addColumns(column3, column4); 
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    ForeignKey foreignKey1 = (ForeignKey)this.pair.getUnit(paramSyncSide.opposite()), foreignKey2 = (ForeignKey)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (foreignKey1 != null && !SyncUtil.c(foreignKey1))
      alterScript.addAll(dbms.alterForeignKey.b(this.pair, foreignKey1)); 
    if (foreignKey2 != null && !SyncUtil.c(foreignKey2))
      alterScript.addAll(dbms.alterForeignKey.a(this.pair, foreignKey2)); 
    return alterScript;
  }
}
