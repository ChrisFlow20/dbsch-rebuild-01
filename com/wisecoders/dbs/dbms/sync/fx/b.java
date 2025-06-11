package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncAction;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

class b extends TreeTableColumn {
  b(FxSynchronizationDialog paramFxSynchronizationDialog, SyncSide paramSyncSide, SyncAction paramSyncAction) {
    Label label = new Label("Action");
    switch (FxSynchronizationDialog$1.a[paramSyncSide.ordinal()]) {
      case 1:
        label.setGraphic((Node)BootstrapIcons.box_arrow_in_left.glyph(new String[0]));
        break;
      case 2:
        label.setGraphic((Node)BootstrapIcons.box_arrow_in_right.glyph(new String[0]));
        label.setContentDisplay(ContentDisplay.RIGHT);
        break;
    } 
    setGraphic((Node)label);
    setMaxWidth(140.0D);
    setMinWidth(100.0D);
    setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    setCellFactory(paramTreeTableColumn -> new a(this.a, paramSyncSide, paramSyncAction));
  }
}
