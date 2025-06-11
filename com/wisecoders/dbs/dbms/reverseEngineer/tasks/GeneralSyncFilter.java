package com.wisecoders.dbs.dbms.reverseEngineer.tasks;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.CommentDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Schema;
import java.util.List;

public class GeneralSyncFilter implements SyncFilter {
  private final TreeSelection a;
  
  private final List b;
  
  private final String c;
  
  public GeneralSyncFilter(TreeSelection paramTreeSelection, List paramList, String paramString) {
    this.a = paramTreeSelection;
    this.b = paramList;
    this.c = paramString;
  }
  
  public boolean rejectDiff(AbstractDiff paramAbstractDiff) {
    if (paramAbstractDiff.pair.right == null) {
      if (paramAbstractDiff.pair.rightParent != null) {
        if (!this.a.isSelected(paramAbstractDiff.pair.rightParent))
          return true; 
        AbstractUnit abstractUnit = paramAbstractDiff.pair.rightParent;
        if (abstractUnit instanceof Schema) {
          Folder folder;
          if (paramAbstractDiff.pair.left instanceof com.wisecoders.dbs.schema.Table) {
            folder = ((Schema)abstractUnit).tables;
          } else if (paramAbstractDiff.pair.left instanceof com.wisecoders.dbs.schema.View) {
            folder = ((Schema)folder).views;
          } else if (paramAbstractDiff.pair.left instanceof com.wisecoders.dbs.schema.Sequence) {
            folder = ((Schema)folder).sequences;
          } else if (paramAbstractDiff.pair.left instanceof com.wisecoders.dbs.schema.Trigger) {
            folder = ((Schema)folder).triggers;
          } else if (paramAbstractDiff.pair.left instanceof com.wisecoders.dbs.schema.Procedure) {
            folder = ((Schema)folder).procedures;
          } else if (paramAbstractDiff.pair.left instanceof com.wisecoders.dbs.schema.Function) {
            folder = ((Schema)folder).functions;
          } 
          if (!this.a.isSelected(folder))
            return true; 
        } 
      } 
    } else if (!this.a.isSelected(paramAbstractDiff.pair.right)) {
      return true;
    } 
    if (paramAbstractDiff.pair.left instanceof ForeignKey && this.b != null && this.b
      .contains(((ForeignKey)paramAbstractDiff.pair.left).getEntity()) && paramAbstractDiff.pair.right == null)
      return true; 
    if ((paramAbstractDiff.pair.left instanceof Column && ((Column)paramAbstractDiff.pair.left).getSpec() == AttributeSpec.functional) || (paramAbstractDiff.pair.right instanceof Column && ((Column)paramAbstractDiff.pair.right)
      .getSpec() == AttributeSpec.functional))
      return true; 
    if (paramAbstractDiff instanceof CommentDiff) {
      CommentDiff commentDiff = (CommentDiff)paramAbstractDiff;
      Dbms dbms = Dbms.get(this.c);
      if (commentDiff.pair.getUnit() instanceof com.wisecoders.dbs.schema.Table && !dbms.alterTable.e())
        return true; 
      if (commentDiff.pair.getUnit() instanceof Column && !dbms.alterColumn.e())
        return true; 
    } 
    return false;
  }
}
