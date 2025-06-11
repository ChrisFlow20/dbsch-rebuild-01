package com.wisecoders.dbs.loader.fx;

import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

class f extends ListCell {
  public void a(Table paramTable, boolean paramBoolean) {
    super.updateItem(paramTable, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean) {
      setText(paramTable.getNameWithSchemaName());
      setGraphic((Node)Rx.q(paramTable.getSymbolicIcon()));
    } 
  }
}
