package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ConstraintExistsDiff extends AbstractExistsDiff {
  public ConstraintExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Constraint constraint = (Constraint)this.pair.getUnit(paramSyncSide.opposite());
    if (constraint != null) {
      Table table = (Table)this.pair.getParent(paramSyncSide);
      Constraint constraint1 = table.createConstraint(constraint.getName());
      constraint1.setText(constraint.getText());
      constraint1.setType(constraint.getType());
      constraint1.setVirtual((constraint.isVirtual() || isMergeVirtual()));
      constraint1.setOptions(constraint.getOptions());
      constraint1.setComment(constraint.getComment());
      constraint1.setCommentTags(constraint.getCommentTags());
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Constraint constraint1 = (Constraint)this.pair.getUnit(paramSyncSide.opposite()), constraint2 = (Constraint)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (constraint2 != null && !SyncUtil.c(constraint2)) {
      alterScript.addAll(dbms.alterConstraint.a(this.pair, constraint2));
      if (constraint2.getComment() != null && dbms.alterConstraint.f())
        alterScript.add(dbms.alterConstraint.b(this.pair, null, constraint2)); 
    } 
    if (constraint1 != null && (constraint1.getEntity() == null || !SyncUtil.c(constraint1)))
      alterScript.addAll(dbms.alterConstraint.b(this.pair, constraint1)); 
    return alterScript;
  }
}
