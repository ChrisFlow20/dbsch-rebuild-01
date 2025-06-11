package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncAction;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiffFilter;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

public class SyncTreeItem extends TreeItem {
  private final List a = new ArrayList();
  
  public SyncTreeItem(SyncDiff paramSyncDiff) {
    super(paramSyncDiff);
    if (paramSyncDiff instanceof SyncPair) {
      SyncPair syncPair = (SyncPair)paramSyncDiff;
      for (SyncPair syncPair1 : syncPair.getChildrenPairs()) {
        SyncPair syncPair2 = syncPair1;
        if (syncPair2.canSkipInDisplayAndShowChildrenDiffs()) {
          for (AbstractDiff abstractDiff : syncPair2.getDifferences())
            this.a.add(new SyncTreeItem(abstractDiff)); 
          continue;
        } 
        this.a.add(new SyncTreeItem(syncPair2));
      } 
      for (AbstractDiff abstractDiff : syncPair.getDifferences())
        this.a.add(new SyncTreeItem(abstractDiff)); 
      getChildren().addAll(this.a);
      setExpanded(true);
    } 
  }
  
  void a(SyncDiffFilter paramSyncDiffFilter) {
    getChildren().retainAll(this.a);
    byte b = 0;
    for (SyncTreeItem syncTreeItem : this.a) {
      if (syncTreeItem.getValue() == null || ((SyncDiff)syncTreeItem.getValue()).matches(paramSyncDiffFilter)) {
        if (!getChildren().contains(syncTreeItem))
          getChildren().add(b, syncTreeItem); 
        b++;
        syncTreeItem.a(paramSyncDiffFilter);
        continue;
      } 
      getChildren().remove(syncTreeItem);
    } 
  }
  
  public void a(SyncAction paramSyncAction) {
    ((SyncDiff)getValue()).setAction(paramSyncAction, false);
    for (TreeItem treeItem : getChildren()) {
      SyncTreeItem syncTreeItem = (SyncTreeItem)treeItem;
      ((SyncDiff)syncTreeItem.getValue()).setAction(paramSyncAction, false);
      syncTreeItem.a(paramSyncAction);
    } 
  }
}
