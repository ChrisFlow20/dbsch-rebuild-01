package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class TableConstraintDiff extends AbstractDiff {
  public TableConstraintDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    Constraint constraint = (Constraint)this.pair.getUnit(paramSyncSide);
    return constraint.getText();
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Constraint constraint1 = (Constraint)this.pair.getUnit(paramSyncSide.opposite()), constraint2 = (Constraint)this.pair.getUnit(paramSyncSide);
    constraint2.setText(constraint1.getText());
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Dbms dbms = Dbms.get(paramString);
    Constraint constraint1 = (Constraint)this.pair.getUnit(paramSyncSide.opposite()), constraint2 = (Constraint)this.pair.getUnit(paramSyncSide);
    if (constraint2 != null && !SyncUtil.c(constraint2))
      alterScript.addAll(dbms.alterConstraint.a(this.pair, constraint2)); 
    if (constraint1 != null && !SyncUtil.c(constraint1))
      alterScript.addAll(dbms.alterConstraint.b(this.pair, constraint1)); 
    return alterScript;
  }
  
  public boolean leftUnitConditionTextIsInsideRightUnitConditionText() {
    if (this.pair.left != null && this.pair.right != null) {
      String str1 = ((Constraint)this.pair.left).getText(), str2 = ((Constraint)this.pair.right).getText();
      if (str1 != null && str2 != null) {
        Pattern pattern = Pattern.compile("(\\w+)", 2);
        Matcher matcher = pattern.matcher(str1);
        while (matcher.find()) {
          String str = matcher.group(1);
          if (!str2.contains(str))
            return false; 
        } 
        return true;
      } 
    } 
    return false;
  }
}
