package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class RenameDiff extends AbstractDiff {
  private boolean b = false;
  
  public RenameDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    return this.pair.getUnit(paramSyncSide).getName();
  }
  
  public String getOperationString(SyncSide paramSyncSide) {
    return "Rename";
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    AbstractUnit abstractUnit = this.pair.getUnit(paramSyncSide);
    abstractUnit.rename(this.pair.getUnit(paramSyncSide.opposite()).getName());
  }
  
  public void setRenameDoneByOtherDiff(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    AbstractUnit abstractUnit1 = this.pair.getUnit(paramSyncSide.opposite()), abstractUnit2 = this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (this.b || SyncUtil.c(abstractUnit2) || (abstractUnit2.getEntity() != null && abstractUnit2.getEntity().isVirtual()))
      return alterScript; 
    if (abstractUnit1 instanceof Schema) {
      alterScript.addAll(dbms.alterSchema.a(this.pair, (Schema)abstractUnit1, (Schema)abstractUnit2));
    } else if (abstractUnit1 instanceof Table) {
      alterScript.addAll(dbms.alterTable.a(this.pair, (Table)abstractUnit1, (Table)abstractUnit2));
    } else if (abstractUnit1 instanceof Column) {
      alterScript.addAll(dbms.alterColumn.a(this.pair, (Column)abstractUnit1, (Column)abstractUnit2));
    } else if (abstractUnit1 instanceof UserDataType) {
      alterScript.addAll(dbms.alterUserDataType.a(this.pair, (UserDataType)abstractUnit1, (UserDataType)abstractUnit2));
    } else if (abstractUnit1 instanceof Constraint) {
      alterScript.addAll(dbms.alterConstraint.a(this.pair, (Constraint)abstractUnit1, (Constraint)abstractUnit2));
    } else if (abstractUnit1 instanceof ForeignKey) {
      alterScript.addAll(dbms.alterForeignKey.a(this.pair, (ForeignKey)abstractUnit1, (ForeignKey)abstractUnit2));
    } else if (abstractUnit1 instanceof Index) {
      Index index = (Index)abstractUnit1;
      alterScript.addAll(dbms.getAlterIndex(index.getType()).a(this.pair, (Index)abstractUnit1, (Index)abstractUnit2));
    } else if (abstractUnit1 instanceof ChildEntity) {
      alterScript.addAll(dbms.alterTable.a(this.pair, (ChildEntity)abstractUnit1, (ChildEntity)abstractUnit2));
    } 
    alterScript.setSortOrder(600);
    return alterScript;
  }
}
