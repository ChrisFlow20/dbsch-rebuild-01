package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import javafx.geometry.Pos;
import javafx.scene.control.TreeTableCell;

class c extends TreeTableCell {
  private final SyncSide a;
  
  c(SyncSide paramSyncSide) {
    this.a = paramSyncSide;
    setAlignment(Pos.CENTER);
  }
  
  protected void a(AbstractDiff paramAbstractDiff, boolean paramBoolean) {
    super.updateItem(paramAbstractDiff, paramBoolean);
    setText(null);
    getStyleClass().removeAll((Object[])new String[] { "sync-exists", "sync-missing", "sync-changed" });
    setTooltip(null);
    if (!paramBoolean && paramAbstractDiff != null) {
      setText(paramAbstractDiff.getDiffString(this.a));
      switch (SyncTextColumn$1.a[paramAbstractDiff.getOperationType(this.a).ordinal()]) {
        case 1:
          getStyleClass().add("sync-exists");
          break;
        case 2:
          getStyleClass().add("sync-missing");
          break;
        case 3:
          getStyleClass().add("sync-changed");
          break;
      } 
    } 
  }
}
