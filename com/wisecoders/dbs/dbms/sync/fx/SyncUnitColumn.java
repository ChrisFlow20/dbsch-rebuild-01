package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

public class SyncUnitColumn extends TreeTableColumn {
  public SyncUnitColumn() {
    super("Schema Item");
    setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          if (object instanceof SyncPair) {
            SyncPair syncPair = (SyncPair)object;
            return (ObservableValue)new ReadOnlyObjectWrapper(syncPair.getUnit());
          } 
          if (object instanceof AbstractDiff) {
            AbstractDiff abstractDiff = (AbstractDiff)object;
            return (ObservableValue)new ReadOnlyObjectWrapper(abstractDiff.pair.getUnit());
          } 
          return null;
        });
    setCellFactory(paramTreeTableColumn -> new d());
  }
}
