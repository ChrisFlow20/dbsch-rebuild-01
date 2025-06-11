package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.MaterializedView;
import java.util.ArrayList;

public class MaterializedViewScriptDiff extends AbstractDiff {
  public MaterializedViewScriptDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    return "script";
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    MaterializedView materializedView1 = (MaterializedView)this.pair.getUnit(paramSyncSide.opposite()), materializedView2 = (MaterializedView)this.pair.getUnit(paramSyncSide);
    materializedView2.setScript(materializedView1.getScript());
    ArrayList<Column> arrayList = new ArrayList();
    for (Column column1 : materializedView1.columns) {
      Column column2 = (Column)materializedView2.columns.getByName(column1.getName());
      if (column2 == null)
        column2 = materializedView2.createColumn(column1.getName(), column1.getDataType()); 
      column2.setDataType(column1.getDataType());
      arrayList.add(column2);
    } 
    for (Column column : materializedView2.columns) {
      if (!arrayList.contains(column))
        column.markForDeletion(); 
    } 
    materializedView2.refresh();
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Dbms dbms = Dbms.get(paramString);
    MaterializedView materializedView1 = (MaterializedView)this.pair.getUnit(paramSyncSide.opposite()), materializedView2 = (MaterializedView)this.pair.getUnit(paramSyncSide);
    if (materializedView2 != null && !SyncUtil.c(materializedView2))
      alterScript.addAll(dbms.alterMaterializedView.a(this.pair, materializedView2)); 
    if (materializedView1 != null && !SyncUtil.c(materializedView1))
      alterScript.addAll(dbms.alterMaterializedView.b(this.pair, materializedView1)); 
    return alterScript;
  }
}
