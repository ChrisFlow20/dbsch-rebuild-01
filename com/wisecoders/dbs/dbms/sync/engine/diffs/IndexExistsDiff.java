package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class IndexExistsDiff extends AbstractExistsDiff {
  public IndexExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Index index = (Index)this.pair.getUnit(paramSyncSide.opposite());
    if (index != null) {
      Table table = (Table)this.pair.getParent(paramSyncSide);
      Index index1 = table.createIndex(index.getName());
      index1.setType(index.getType());
      index1.setComment(index.getComment());
      index1.setVirtual((index.isVirtual() || isMergeVirtual()));
      index1.setCommentTags(index.getCommentTags());
      index1.setOptions(index.getOptions());
      for (Column column1 : index.getColumns()) {
        Column column2 = table.getColumnByNameOrPath(column1.getNameWithPath());
        if (column2 == null) {
          Column column = table.createColumn(column1.getName(), column1.getDataType());
          column.setSpec(column1.getSpec());
          index1.addColumn(column);
          continue;
        } 
        index1.addColumn(column2);
      } 
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Index index1 = (Index)this.pair.getUnit(paramSyncSide.opposite()), index2 = (Index)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (index2 != null && !SyncUtil.c(index2))
      alterScript.addAll(dbms.getAlterIndex(index2.getType()).a(this.pair, index2)); 
    if (index1 != null && !SyncUtil.c(index1))
      alterScript.addAll(dbms.getAlterIndex(index1.getType()).c(this.pair, index1)); 
    return alterScript;
  }
}
