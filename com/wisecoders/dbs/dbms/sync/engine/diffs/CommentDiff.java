package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;

@DoNotObfuscate
public class CommentDiff extends AbstractDiff {
  public CommentDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    String str = this.pair.getUnit(paramSyncSide).getComment();
    if (str == null || str.isEmpty())
      return "Comment : "; 
    return "Comment : " + StringUtil.cutOfWithDots(str, 80);
  }
  
  public String getOperationString(SyncSide paramSyncSide) {
    String str = this.pair.getUnit(paramSyncSide).getComment();
    if (str == null || str.isEmpty())
      return "Comment"; 
    return "Uncomment";
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    AbstractUnit abstractUnit1 = this.pair.getUnit(paramSyncSide.opposite()), abstractUnit2 = this.pair.getUnit(paramSyncSide);
    abstractUnit2.setComment(abstractUnit1.getComment());
    abstractUnit2.setCommentTags(abstractUnit1.getCommentTags());
    if (paramGenericLayoutPane != null)
      paramGenericLayoutPane.c(); 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AbstractUnit abstractUnit1 = this.pair.getUnit(paramSyncSide.opposite()), abstractUnit2 = this.pair.getUnit(paramSyncSide);
    AlterScript alterScript = new AlterScript(paramString);
    Dbms dbms = Dbms.get(paramString);
    if (SyncUtil.c(abstractUnit1))
      return alterScript; 
    if (abstractUnit1 instanceof Schema && dbms.alterSchema.e())
      alterScript.add(dbms.alterSchema.b(this.pair, (Schema)abstractUnit1, (Schema)abstractUnit2)); 
    if (abstractUnit1 instanceof Table && dbms.alterTable.e())
      alterScript.add(dbms.alterTable.b(this.pair, (Table)abstractUnit1, (Table)abstractUnit2)); 
    if (abstractUnit1 instanceof MaterializedView && dbms.alterMaterializedView.c()) {
      alterScript.add(dbms.alterMaterializedView.a(this.pair, (MaterializedView)abstractUnit1, (MaterializedView)abstractUnit2));
    } else if (abstractUnit1 instanceof View && dbms.alterView.c()) {
      alterScript.add(dbms.alterView.a(this.pair, (View)abstractUnit1, (View)abstractUnit2));
    } 
    if (abstractUnit1 instanceof Index) {
      Index index = (Index)abstractUnit1;
      if (dbms.getAlterIndex(index.getType()).e())
        alterScript.add(dbms.getAlterIndex(index.getType()).b(this.pair, (Index)abstractUnit1, (Index)abstractUnit2)); 
    } 
    if (abstractUnit1 instanceof Column && dbms.alterColumn.e())
      alterScript.add(dbms.alterColumn.b(this.pair, (Column)abstractUnit1, (Column)abstractUnit2)); 
    if (abstractUnit1 instanceof Constraint && dbms.alterConstraint.f())
      alterScript.add(dbms.alterConstraint.b(this.pair, (Constraint)abstractUnit1, (Constraint)abstractUnit2)); 
    if (abstractUnit1 instanceof ForeignKey && dbms.alterForeignKey.e())
      alterScript.add(dbms.alterForeignKey.b(this.pair, (ForeignKey)abstractUnit1, (ForeignKey)abstractUnit2)); 
    if (abstractUnit1 instanceof Sequence && dbms.alterSequence.c())
      alterScript.add(dbms.alterSequence.a(this.pair, (Sequence)abstractUnit1, (Sequence)abstractUnit2)); 
    if (abstractUnit1 instanceof UserDataType && dbms.alterUserDataType.d())
      alterScript.add(dbms.alterUserDataType.b(this.pair, (UserDataType)abstractUnit1, (UserDataType)abstractUnit2)); 
    if (abstractUnit1 instanceof Sql && dbms.alterPlSql.a((Sql)abstractUnit1))
      alterScript.addAll(dbms.alterPlSql.a(this.pair, (Sql)abstractUnit1, (Sql)abstractUnit2)); 
    return alterScript;
  }
}
