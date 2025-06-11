package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

public class SyncTextColumn extends TreeTableColumn {
  private static final String a = "sync-exists";
  
  private static final String b = "sync-changed";
  
  private static final String c = "sync-missing";
  
  SyncTextColumn(SyncSide paramSyncSide) {
    super("Value");
    setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          return (object instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff) ? (ObservableValue)new ReadOnlyObjectWrapper(object) : null;
        });
    setCellFactory(paramTreeTableColumn -> new c(paramSyncSide));
  }
}
