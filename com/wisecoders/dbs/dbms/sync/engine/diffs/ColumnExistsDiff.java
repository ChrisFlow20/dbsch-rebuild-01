package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ColumnExistsDiff extends AbstractExistsDiff {
  public ColumnExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Column column = (Column)this.pair.getUnit(paramSyncSide.opposite());
    if (column != null) {
      AbstractUnit abstractUnit = this.pair.getParent(paramSyncSide);
      if (abstractUnit instanceof Table) {
        Table table = (Table)abstractUnit;
        Column column1 = table.createColumn(column.getName(), column.getDataType());
        column1.cloneFrom(column);
        if (isMergeVirtual())
          column1.setVirtual(true); 
        if (column.getEntity() != null) {
          int i = column.getEntity().getAttributes().indexOf(column);
          if (i > -1 && i < table.columns.size() - 1) {
            table.columns.remove(column1);
            table.columns.add(i, column1);
          } 
        } 
      } else if (abstractUnit instanceof ChildEntity) {
        Column column1 = ((ChildEntity)abstractUnit).createColumn(column.getName(), column.getDataType());
        column1.cloneFrom(column);
      } else if (abstractUnit instanceof Column) {
        Column column1 = ((Column)abstractUnit).getChildEntity().createColumn(column.getName(), column.getDataType());
        column1.cloneFrom(column);
      } 
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Column column1 = (Column)this.pair.getUnit(paramSyncSide.opposite()), column2 = (Column)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (column2 != null && !SyncUtil.c(column2)) {
      alterScript.addAll(dbms.alterColumn.a(this.pair, column2));
      if (column2.getComment() != null && dbms.alterColumn.e())
        alterScript.add(dbms.alterColumn.b(this.pair, null, column2)); 
    } 
    if (column1 != null && !SyncUtil.c(column1))
      alterScript.addAll(dbms.alterColumn.d(this.pair, column1)); 
    return alterScript;
  }
}
