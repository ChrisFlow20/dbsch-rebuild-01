package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.View;

public class MaterializedViewExistsDiff extends AbstractExistsDiff {
  public MaterializedViewExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    MaterializedView materializedView = (MaterializedView)this.pair.getUnit(paramSyncSide.opposite());
    if (materializedView != null) {
      Schema schema = (Schema)this.pair.getParent(paramSyncSide);
      View view = a(schema, materializedView);
      if (paramGenericLayoutPane != null)
        paramGenericLayoutPane.a(view); 
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    MaterializedView materializedView1 = (MaterializedView)this.pair.getUnit(paramSyncSide.opposite()), materializedView2 = (MaterializedView)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (materializedView2 != null && !SyncUtil.c(materializedView2))
      alterScript.addAll(dbms.alterMaterializedView.a(this.pair, materializedView2)); 
    if (materializedView1 != null && !SyncUtil.c(materializedView1))
      alterScript.addAll(dbms.alterMaterializedView.b(this.pair, materializedView1)); 
    return alterScript;
  }
  
  protected View a(Schema paramSchema, View paramView) {
    MaterializedView materializedView = paramSchema.createMaterializedView(paramView.getName());
    materializedView.setScript(paramView.getScript());
    materializedView.setComment(paramView.getComment());
    materializedView.setCommentTags(paramView.getCommentTags());
    materializedView.setVirtual((paramView.isVirtual() || isMergeVirtual()));
    for (Column column1 : paramView.getAttributes()) {
      Column column2 = materializedView.createColumn(column1.getName(), column1.getDataType());
      column2.cloneFrom(column1);
    } 
    return materializedView;
  }
}
