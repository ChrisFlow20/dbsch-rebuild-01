package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class ViewExistsDiff extends AbstractExistsDiff {
  public ViewExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    View view = (View)this.pair.getUnit(paramSyncSide.opposite());
    if (view != null) {
      Schema schema = (Schema)this.pair.getParent(paramSyncSide);
      View view1 = a(schema, view);
      if (paramGenericLayoutPane != null)
        paramGenericLayoutPane.a(view1); 
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    View view1 = (View)this.pair.getUnit(paramSyncSide.opposite()), view2 = (View)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (view2 != null && !SyncUtil.c(view2))
      alterScript.addAll(dbms.alterView.a(this.pair, view2)); 
    if (view1 != null && !SyncUtil.c(view1))
      alterScript.addAll(dbms.alterView.b(this.pair, view1)); 
    return alterScript;
  }
  
  private View a(Schema paramSchema, View paramView) {
    View view = paramSchema.createView(paramView.getName());
    view.setScript(paramView.getScript());
    view.setVirtual((paramView.isVirtual() || isMergeVirtual()));
    view.setSyncPriority(paramView.getSyncPriority());
    view.setComment(paramView.getComment());
    view.setCommentTags(paramView.getCommentTags());
    for (Column column1 : paramView.getAttributes()) {
      Column column2 = view.createColumn(column1.getName(), column1.getDataType());
      column2.cloneFrom(column1);
    } 
    return view;
  }
}
