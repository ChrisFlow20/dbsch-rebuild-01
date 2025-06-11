package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import javafx.geometry.Pos;
import javafx.scene.control.TreeTableCell;

class k extends TreeTableCell {
  k() {
    setAlignment(Pos.CENTER);
  }
  
  protected void a(AbstractDiff paramAbstractDiff, boolean paramBoolean) {
    super.updateItem(paramAbstractDiff, paramBoolean);
    setText(null);
    getStyleClass().removeAll((Object[])new String[] { "sync-exists", "sync-missing", "sync-changed" });
    if (!paramBoolean && paramAbstractDiff != null) {
      String str = paramAbstractDiff.getDiffString(SyncSide.left);
      if ("EXIST".equals(str)) {
        str = "Created";
      } else if ("MISSING".equals(str)) {
        str = "Dropped";
      } 
      setText(str);
      switch (SyncGitTextColumn$1.a[paramAbstractDiff.getOperationType(SyncSide.left).ordinal()]) {
        case 1:
          getStyleClass().add("sync-missing");
          break;
        case 2:
          getStyleClass().add("sync-exists");
          break;
        case 3:
          getStyleClass().add("sync-changed");
          break;
      } 
    } 
  }
}
