package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.ArrayList;

@DoNotObfuscate
public class ViewScriptDiff extends AbstractDiff {
  public ViewScriptDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    return "View Query";
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    View view1 = (View)this.pair.getUnit(paramSyncSide.opposite()), view2 = (View)this.pair.getUnit(paramSyncSide);
    view2.setScript(view1.getScript());
    ArrayList<Column> arrayList = new ArrayList();
    for (Column column1 : view1.columns) {
      Column column2 = (Column)view2.columns.getByName(column1.getName());
      if (column2 == null)
        column2 = view2.createColumn(column1.getName(), column1.getDataType()); 
      column2.setDataType(column1.getDataType());
      arrayList.add(column2);
    } 
    for (Column column : view2.columns) {
      if (!arrayList.contains(column))
        column.markForDeletion(); 
    } 
    view2.refresh();
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Dbms dbms = Dbms.get(paramString);
    View view1 = (View)this.pair.getUnit(paramSyncSide.opposite()), view2 = (View)this.pair.getUnit(paramSyncSide);
    if (view2 != null && !SyncUtil.c(view2))
      alterScript.addAll(dbms.alterView.a(this.pair, view2)); 
    if (view1 != null && !SyncUtil.c(view1))
      alterScript.addAll(dbms.alterView.b(this.pair, view1)); 
    return alterScript;
  }
}
